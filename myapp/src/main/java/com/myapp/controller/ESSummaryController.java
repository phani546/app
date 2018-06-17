package com.myapp.controller;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.myapp.model.Summary;
import com.vasu.myapp.AWSESConnection;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.params.SearchType;

/**
 * @author Phani
 *
 */
public class ESSummaryController {
	JestClient esCon = AWSESConnection.getInstance().getEsConnection();

	public String insertSummary(Summary summaryObj) {
		JsonObject jsonRequestObject = new JsonObject();
		jsonRequestObject.addProperty("id", summaryObj.getId());
		jsonRequestObject.addProperty("totalAmout", summaryObj.getTotalAmount());
		jsonRequestObject.addProperty("year", summaryObj.getYear());
		jsonRequestObject.addProperty("month", summaryObj.getMonth());

		System.out.println(jsonRequestObject);

		Index index = new Index.Builder(jsonRequestObject).index("summarypage").type("s1")
				.id(jsonRequestObject.get("id").getAsString()).build();
		String result = "";
		try {
			DocumentResult responseFromES = esCon.execute(index);
			if (responseFromES.isSucceeded()) {
				result = "successfully Inserted";
			} else {
				result = "Insertion Failed";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean searchIndexForRecordExistance(String month, String year) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		org.elasticsearch.index.query.TermQueryBuilder termQuerymonth = QueryBuilders.termQuery("month", month);
		org.elasticsearch.index.query.TermQueryBuilder termQueryyear = QueryBuilders.termQuery("year", year);

		BoolQueryBuilder boolQueryOnSummary = QueryBuilders.boolQuery().must(termQuerymonth).must(termQueryyear);
		sourceBuilder.query(boolQueryOnSummary).size(1);

		System.out.println(sourceBuilder);

		Search summarySearch = (Search) new Search.Builder(sourceBuilder.toString()).addIndex("summarypage")
				.addType("s1").setSearchType(SearchType.QUERY_THEN_FETCH).build();

		SearchResult searchresult = null;
		try {
			searchresult = esCon.execute(summarySearch);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Hit<LinkedTreeMap, Void>> signInHits = searchresult.getHits(LinkedTreeMap.class);
		System.out.println("size is " + signInHits.size());
		boolean contains = true;
		if (signInHits.size() == 0) {
			contains = false;
		}
		return contains;
	}

	public double getDataFromIndex(String month, String year) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		org.elasticsearch.index.query.TermQueryBuilder termQuerymonth = QueryBuilders.termQuery("month", month);
		org.elasticsearch.index.query.TermQueryBuilder termQueryyear = QueryBuilders.termQuery("year", year);

		BoolQueryBuilder boolQueryOnSummary = QueryBuilders.boolQuery().must(termQuerymonth).must(termQueryyear);
		sourceBuilder.query(boolQueryOnSummary).size(1);

		System.out.println(sourceBuilder);

		Search summarySearch = (Search) new Search.Builder(sourceBuilder.toString()).addIndex("summarypage")
				.addType("s1").setSearchType(SearchType.QUERY_THEN_FETCH).build();

		SearchResult searchresult = null;
		try {
			searchresult = esCon.execute(summarySearch);
		} catch (IOException e) {
			e.printStackTrace();
		}
		double amount = 0.0;
		List<Hit<LinkedTreeMap, Void>> signInHits = searchresult.getHits(LinkedTreeMap.class);
		for (Hit<LinkedTreeMap, Void> hit : signInHits) {
			amount = Double.valueOf(hit.source.get("totalAmout").toString());
		}
		System.out.println("size is " + signInHits.size());
		boolean contains = true;
		if (signInHits.size() == 0) {
			contains = false;
		}
		return amount;
	}
	public void getArriers(){
		
	}

}
