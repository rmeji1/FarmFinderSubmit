package com.farmfinder.services;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.farmfinder.model.* ;
import com.farmfinder.commands.MetadataRepo ;
import com.farmfinder.config.MongoConfig;
import com.farmfinder.metadata.Metadata ;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * Class Created by: Roberto Mejia
 * */

@Api(value = "/metadata", description = "Metadata REST service for FarmFinder")
@Path("/metadata")
public class MetadataService {
	ObjectMapper mapper = new ObjectMapper();
	
	
	@POST
	@Path("/create/farm")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a Farm Metdata object and store in database", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to store metadata class in database"),
			  @ApiResponse(code = 500, message = "Unable to store in database")
			  })	
	public Response createFarmMetadata(){
		/*Create metadata for farm class using metadata class*/ 
		Metadata meta = new Metadata(Farm.class) ;
		/*Get repo from application context and store the generic metadata for farm. 
		 * Note can edit by getting the ArrayList<HashMap> if needed.*/
		try{
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
			MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
			repo.save(meta) ;
			return Response.status(201).entity(meta).build() ;
		}catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build() ;
		}
	}
	
	@POST
	@Path("/create/category")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a Category Metdata object and store in database", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to store metadata class in database"),
			  @ApiResponse(code = 500, message = "Unable to store in database")
			  })	
	public Response createCategoryMetadata(){
		/*Create metadata for Category class using metadata class*/ 
		Metadata meta = new Metadata(Category.class) ;
		/*Get repo from application context and store the generic metadata for category. 
		 * Note can edit by getting the ArrayList<HashMap> if needed.*/
		try{
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
			MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
			repo.save(meta) ;
			return Response.status(201).entity(meta).build() ;
		}catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build() ;
		}
	}
	
	@POST
	@Path("/create/product")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a product Metdata object and store in database", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to store metadata class in database"),
			  @ApiResponse(code = 500, message = "Unable to store in database")
			  })	
	public Response createProductMetadata(){
		/*Create metadata for Product class using metadata class*/ 
		Metadata meta = new Metadata(Product.class) ;
		/*Get repo from application context and store the generic metadata for product. 
		 * Note can edit by getting the ArrayList<HashMap> if needed.*/
		try{
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
			MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
			repo.save(meta) ;
			return Response.status(201).entity(meta).build() ;
		}catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build() ;
		}
	}
	
	@POST
	@Path("/create/payment")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Creates a payment Metdata object and store in database", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to store metadata class in database"),
			  @ApiResponse(code = 500, message = "Unable to store in database")
			  })	
	public Response createPaymentMeta(){
		/*Create metadata for Product class using metadata class*/ 
		Metadata meta = new Metadata(Payment.class) ;
		/*Get repo from application context and store the generic metadata for product. 
		 * Note can edit by getting the ArrayList<HashMap> if needed.*/
		try{
			ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
			MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
			repo.save(meta) ;
			return Response.status(201).entity(meta).build() ;
		}catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build() ;
		}
	}
	
	
	@GET
	@Path("/farm")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get farm metadata from metadataa collection", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get farm metadata"),
			  @ApiResponse(code = 500, message = "Unable to get farm metadata")
			  })	
	public Response getFarmMetadata() {
		/*Get repo from application context and get metadata for farm. */
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
		try{
			Metadata meta = repo.findByTitle("Farm") ;
			return Response.status(200).entity(meta).build();
		}
		catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build();
		}
	}

	@GET
	@Path("/categories")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get categories metadata from metadataa collection", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get category metadata"),
			  @ApiResponse(code = 500, message = "Unable to get category metadata")
			  })	
	public Response getCategoryMetadata() {
		/*Get repo from application context and get metadata for farm. */
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
		try{
			Metadata meta = repo.findByTitle("Category") ;
			return Response.status(200).entity(meta).build();
		}
		catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build();
		}
	}
	
	@GET
	@Path("/product")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get product metadata from metadataa collection", notes = "Created by: Roberto Mejia", response=Metadata.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get product metadata"),
			  @ApiResponse(code = 500, message = "Unable to get product metadata")
			  })	
	public Response getProductMetadata() {
		/*Get repo from application context and get metadata for farm. */
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MetadataRepo repo = (MetadataRepo) ctx.getBean(MetadataRepo.class) ;
		try{
			Metadata meta = repo.findByTitle("Product") ;
			return Response.status(200).entity(meta).build();
		}
		catch(Exception e){
			e.printStackTrace() ;
			return Response.status(500).build();
		}
			
	}
}

