package com.myapp.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.myapp.model.Expenses;
import com.myapp.model.Summary;
import com.vasu.myapp.AWSESConnection;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchResult.Hit;
import io.searchbox.core.Update;
import io.searchbox.params.SearchType;
/**
 * @author Phani
 *
 */
public class ESExpensesController {
   JestClient esCon=AWSESConnection.getInstance().getEsConnection();
   Expenses expense=new Expenses();
   public String updateToES(Expenses expenseObj) {
	     JsonObject jsonRequestObject=new JsonObject();
	     JsonObject docOjb=new JsonObject();
	     jsonRequestObject.addProperty("expensename", expenseObj.getDescription());
		 jsonRequestObject.addProperty("amount", expenseObj.getAmount());
		 jsonRequestObject.addProperty("category", expenseObj.getCategory());
		 jsonRequestObject.addProperty("created", expenseObj.getDate());
		 jsonRequestObject.addProperty("remarks", expenseObj.getRemarks());
		 jsonRequestObject.addProperty("isArrier", expenseObj.isArrier());
		 jsonRequestObject.addProperty("arrierTo", expenseObj.getArrierTo());
		 jsonRequestObject.addProperty("isBorrowed", expenseObj.isBorrowed());
		 jsonRequestObject.addProperty("borrowedBy", expenseObj.getBorrowedTo());
		 jsonRequestObject.addProperty("url", expenseObj.getUrl());
		 
		 System.out.println(expenseObj.getId());
		 docOjb.add("doc",jsonRequestObject);
		 
		 System.out.println(docOjb);
		 
		 Update update = new Update.Builder(docOjb).index("expenses_management").type("expensesv1").id(expenseObj.getId()).build();
		 String msg="";
		 try {
			DocumentResult updateResult = esCon.execute(update);
			if(updateResult.isSucceeded()) {
				msg="updated successfully";
			}else {
				msg="updatton failed";
			}
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		return msg;
   }
   public String insertToES(Expenses expenseObj) {
	     JsonObject jsonRequestObject=new JsonObject();
	     
		 jsonRequestObject.addProperty("expensename", expenseObj.getDescription());
		 jsonRequestObject.addProperty("amount", expenseObj.getAmount());
		 jsonRequestObject.addProperty("category", expenseObj.getCategory());
		 jsonRequestObject.addProperty("created", expenseObj.getDate());
		 jsonRequestObject.addProperty("remarks", expenseObj.getRemarks());
		 jsonRequestObject.addProperty("isArrier", expenseObj.isArrier());
		 jsonRequestObject.addProperty("arrierTo", expenseObj.getArrierTo());
		 jsonRequestObject.addProperty("isBorrowed", expenseObj.isBorrowed());
		 jsonRequestObject.addProperty("borrowedBy", expenseObj.getBorrowedTo());
		 jsonRequestObject.addProperty("url", expenseObj.getUrl());
		 
		 System.out.println(jsonRequestObject);
		 
		 Index index = new Index.Builder(jsonRequestObject).index("expenses_management").type("expensesv1").id(expenseObj.getId()).build();
		 String result="";
		 try {
			DocumentResult responseFromES=esCon.execute(index);
			if(responseFromES.isSucceeded()){
				result = "successfully Inserted";
			}else{
				result = "Insertion Failed";
			}
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		 return result;
   }
   public List<Expenses> getData(String from, String to){
	  SearchSourceBuilder searchBuilder=new SearchSourceBuilder();
	  org.elasticsearch.index.query.RangeQueryBuilder rangeBuilder= QueryBuilders.rangeQuery("created").from(from).to(to);
	  searchBuilder.query(rangeBuilder).size(50);
	  
	  Search searchQuery = (Search) new Search.Builder(searchBuilder.toString())
															.addIndex("expenses_management")
															.addType("expensesv1")
															.setSearchType(SearchType.QUERY_THEN_FETCH)
															.build();
	  List<Expenses> lmp =new ArrayList<>();
	  try {
		  SearchResult Searchresult = esCon.execute(searchQuery);
		  if(Searchresult.getResponseCode()==200) {
		    List<Hit<LinkedTreeMap,Void>> orderHits =  Searchresult.getHits(LinkedTreeMap.class);
		        for (SearchResult.Hit<LinkedTreeMap,Void> srcObj : orderHits ) {
		        	System.out.println(srcObj.source);
		    		Expenses expMesure= new Expenses();
		    		expMesure.setId(srcObj.id);
			    	expMesure.setDescription(srcObj.source.get("expensename").toString());
		    		expMesure.setAmount(Double.parseDouble(srcObj.source.get("amount").toString()));
		    		expMesure.setCategory(srcObj.source.get("category").toString());
		    		expMesure.setSubCategory((String)srcObj.source.get("subcategory"));
		    		expMesure.setDate(srcObj.source.get("created").toString());
		    		expMesure.setRemarks(srcObj.source.get("remarks").toString());
		    		expMesure.setArrier(Boolean.valueOf(srcObj.source.get("isArrier").toString()));
		    		expMesure.setArrierTo(srcObj.source.get("arrierTo").toString());
		    		expMesure.setBorrowed(Boolean.valueOf(srcObj.source.get("isBorrowed").toString()));
		    		expMesure.setBorrowedTo(srcObj.source.get("borrowedBy").toString());
		    		
		    	    lmp.add(expMesure);
		        }
		  }
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	return lmp;
   }
   public String insertSummary(Summary summaryObj){
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
   public Date StringToDate(String dateString) throws ParseException {
	 DateFormat dateFormatLogin = new SimpleDateFormat("yyyy-MM-dd");
  	 Date date = dateFormatLogin.parse(dateString);
  	 return date;
   }
   public String DateToString(Date dateComing) {
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    String strDate = dateFormat.format(dateComing);  
	    return strDate;
   }
}
