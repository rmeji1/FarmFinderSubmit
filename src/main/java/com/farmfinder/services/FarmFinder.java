package com.farmfinder.services;

import java.io.IOException;



import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.BSONObject;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.farmfinder.commands.CategoryRepo;
import com.farmfinder.commands.ProductRepo;
import com.farmfinder.config.MongoConfig;
import com.farmfinder.commands.FarmRepo ;
import com.farmfinder.model.* ;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

@Path("/farmfinder")
@Api(value = "/farmfinder", description = "Main REST service for FarmFinder")
@Produces({"application/json"})
public class FarmFinder {
	ObjectMapper mapper = new ObjectMapper();
	
	@POST
	@Path("/createFarm")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "This function is not useful and should be removed", notes = "More notes about this method", response=Farm.class)
	public Response createFarm(){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		CategoryRepo repo = (CategoryRepo) ctx.getBean(CategoryRepo.class) ;
		/*Create new category class*/ 
		Category cat = new Category() ;
		cat.setName("Strawberry") ;		
		repo.save(cat) ;
		return Response.status(201).entity(cat).build() ;
	}
	
/* add products to cart  Gibsan Abdu*/
	
	@PUT
	@Path("/addtoCart")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "This adds a product to a cart.", notes = "Created by: Gibsan Abdu", response=Cart.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 201, message = "Put card data into session"),
			  @ApiResponse(code = 404, message = "Error occured") 
			})
	public Response addtoCart( @ApiParam(value = "Request to add cart to session", required = true) @Context HttpServletRequest request, @ApiParam(value = "The cart data that will be added", required = true)String cartData){
		System.out.println(cartData);
		String quantity = null ;
		ObjectMapper mapper = new ObjectMapper() ;
		Product product = null ;
		
		try{
			CartQuant cartquant = mapper.readValue(cartData, CartQuant.class) ;
			product = mapper.readValue(cartquant.getData(), Product.class) ;
			quantity = cartquant.getQuantity() ;			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("name", product.getName());
			map.put("price", Double.toString(product.getPrice()));
			map.put("quantity", quantity);
			
			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cartSession");
			 double totalp = 0;
			if(cart==null){
				cart = new Cart();
				cart.addtoList(map);
	            totalp = Double.parseDouble(quantity) * product.getPrice(); 
			}else {
				totalp = cart.getTotalPrice() + Double.parseDouble(quantity) * product.getPrice(); 
				cart.addtoList(map);
			}
			cart.setTotalPrice(totalp);
			session.setAttribute("cartSession", cart);
			System.out.println(cart.getTotalPrice());
			return Response.status(201).entity(cart).build();
		}catch(Exception e){
			e.printStackTrace();
		}
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		ProductRepo repo = (ProductRepo) ctx.getBean(ProductRepo.class) ;
		
		return Response.status(500).entity("error").build();

	}
	
	/*.................................................................................... */
	/*  List products stored in cart  GIBSAN ABDU */	
	@GET
	@Path("/listallCart")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "List everything inside cart", notes = "Created by: Gibsan Abdu", response=Cart.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get cart information from session and return as JSON")
			})
	public Response listallCart(@ApiParam(value = "Request to get cart from session", required = true) @Context HttpServletRequest request){	
		HttpSession session = request.getSession(false);
		Cart cart = (Cart) session.getAttribute("cartSession");
		return Response.status(200).entity(cart).build();
	}
	
	/*.................................................................................... */
	/*Delete single product stored in cart  GIBSAN ABDU */	
	@DELETE
	@Path("/deleteCart/{index}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete Single item from cart", notes = "Created by: Gibsan Abdu", response=Cart.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to delete cart information from session and return the item index.")
			})
	public Response deleteCart(@ApiParam(value = "Request to add cart to session", required = true) @Context HttpServletRequest request,@ApiParam(value = "Cart product index.", required = true) @PathParam("index")String index){
	
		HttpSession session = request.getSession(false);
		Cart cart = (Cart) session.getAttribute("cartSession");
		List <HashMap> cartList = cart.getProdlst();
		cartList.remove(Integer.parseInt(index));
		return Response.status(200).entity(index).build();	
	}

	/*.................................................................................... *
	/*Delete all product stored in cart GIBSAN ABDU */
	@DELETE
	@Path("/deleteAllCart/")
	@Produces(MediaType.APPLICATION_JSON)	
	@ApiOperation(value = "Delete all items from cart", notes = "Created by: Gibsan Abdu", response=Cart.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to delete all cart information from session.")
			})
	public Response deleteAllCart(@ApiParam(value = "Request to add cart to session", required = true) @Context HttpServletRequest request){
		HttpSession session = request.getSession(false);
		Cart cart = (Cart) session.getAttribute("cartSession");
		List <HashMap> cartList = cart.getProdlst();
		cartList.clear();
		return Response.status(200).entity("").build();	
	}
	/*------------------------------------------------------------------------
	 * URL: rest/farmfinder/farm -X POST
	 * Goal: Get json and create a new farm
	 * Created By: Roberto Mejia
	 */
	@POST
	@Path("/farm")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create new farm from json data used in index.html", notes = "Created by: Roberto Mejia", response=Farm.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to create Farm"),
			  @ApiResponse(code = 500, message = "Unable to create Farm")
			})
	public Response createFarm(@ApiParam(value = "Farm JSON data", required = true)String jsonData){
		/*Get repo to be used later to save the farm*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class) ;
		FarmRepo repo = (FarmRepo) ctx.getBean(FarmRepo.class) ;
		/*Uses ObjectMapper to convert jsonData to Farm Object*/
		Farm farm;
		try {
			System.out.println(jsonData) ;
			farm = mapper.readValue(jsonData, Farm.class);
			BSONObject roles = new BasicDBObject() ;
			roles.put("name", "user") ;
			BasicDBList list = new BasicDBList() ;
			list.add(roles) ;
			farm.setRoles(list);
			repo.save(farm) ;
			return Response.status(201).entity(farm).build();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).entity(jsonData).build() ; 
	}


	/*---------------------------------------------------
	 *  URL: rest/farmfinder/farm -X GET
	 *  Get all of the farms stored in database
	 *  Created by: Roberto Mejia
	 */
	@GET
	@Path("/farm")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all farms from DB used in index.html", notes = "Created by: Roberto Mejia", response=Farm.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get all farms")
			})
	public Response getAllFarms(){
		/*Get repo to be used later to save the farm*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class) ;
		FarmRepo repo = (FarmRepo) ctx.getBean(FarmRepo.class) ;
		List<Farm> farms = repo.findAll() ;
		return Response.status(200).entity(farms).build();
	}
	
	/*---------------------------------------------------
	 *  URL: rest/farmfinder/farm/page/{num} -X GET
	 *  Get a group of 10 farms specifying by page number
	 *  Created by: Roberto Mejia
	 */
	
	@GET
	@Path("/farm/page/{page}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Goal: get a group of 10 farms for given page number.", notes = "Note: page index starts at 0. Created by: Roberto Mejia", response=Farm.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get farms for a specific page")
			  })
	public Response getFarmByPage(@ApiParam(value = "Index of page", required = true)@PathParam("page") int page){
		/*Get repo to be used later to save the farm*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class) ;
		FarmRepo repo = (FarmRepo) ctx.getBean(FarmRepo.class) ;
		Page<Farm> farms = repo.findAll( new PageRequest(page, 10)) ;
		return Response.status(200).entity(farms).build();
	}
	
	
	/*---------------------------------------------------
	 *  URL: rest/farmfinder/product -X POST
	 *  Get farms by state
	 *  Created by: Roberto Mejia
	 */
	@POST
	@Path("/product")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new product and store to database", notes = "Created by: Roberto Mejia", response=Product.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to store new product"),
			  @ApiResponse(code = 500, message= "Unable to store data to database.")
			  })
	public Response createProduct(@ApiParam(value = "Product data that will be saved", required = true)String data){
		/*Get repo to be used later to save the farm*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class) ;
		ProductRepo repo = (ProductRepo) ctx.getBean(ProductRepo.class) ;
		Product product = null ;
		try {
			System.out.println(data) ;
			product = mapper.readValue(data, Product.class);
			repo.save(product) ;
			return Response.status(201).entity(product).build();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.status(500).entity(data).build() ;
	}
	
	/*---------------------------------------------------
	 *  URL: rest/farmfinder/product
	 *  Get products for given farm_id
	 *  Created by: Roberto Mejia
	 */
	@GET
	@Path("/product/{farm_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Goal: get all products for a farm.", notes = "Created by: Roberto Mejia", response=Farm.class)
	@ApiResponses(value = {
			  @ApiResponse(code = 200, message = "Able to get farms for a specific page")
			  })	
	public Response getProductsForFarm(@ApiParam(value="Farm id", required=true )@PathParam("farm_id") String farm_id){
		/*Get repo to be used later to save the farm*/
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class) ;
		ProductRepo repo = (ProductRepo) ctx.getBean(ProductRepo.class) ;
		List<Product> prods = repo.findByFarmID(farm_id) ;
		return Response.status(200).entity(prods).build() ;
	}
	
}







