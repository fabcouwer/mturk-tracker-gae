package com.tracker.endpoints;

import static com.tracker.ofy.OfyService.ofy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;
import com.tracker.entity.MarketStatistics;

@Api(name = "mturk", description = "The API for mturk-tracker", version = "v1")
public class MarketStatisticsEndpoint {

	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@ApiMethod(name = "marketStatistics.list", path = "marketStatistics/list", httpMethod = HttpMethod.GET)
	public CollectionResponse<MarketStatistics> list(@Named("from") String from,
			@Named("to") String to, @Nullable @Named("cursor") String cursorString) throws ParseException {
		
		List<MarketStatistics> result = new ArrayList<MarketStatistics>();
		
		Query<MarketStatistics> query = ofy().load().type(MarketStatistics.class)
				.filter("timestamp >=", formatter.parse(from))
				.filter("timestamp <=", formatter.parse(to)).limit(1000);

	    if (cursorString != null){
	        query = query.startAt(Cursor.fromWebSafeString(cursorString));
	    }

	    boolean continu = false;
	    QueryResultIterator<MarketStatistics> iterator = query.iterator();
	    while (iterator.hasNext()) {
	    	MarketStatistics marketStatistics = iterator.next();
	    	result.add(marketStatistics);
	        continu = true;
	    }

	    if (continu) {
	        Cursor cursor = iterator.getCursor();
	        return CollectionResponse.<MarketStatistics> builder().setItems(result)
			        .setNextPageToken(cursor.toWebSafeString()).build();
	    }else{
	    	return CollectionResponse.<MarketStatistics> builder().setItems(result).build();
	    }
	}

}