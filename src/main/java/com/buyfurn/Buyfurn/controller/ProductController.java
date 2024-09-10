package com.buyfurn.Buyfurn.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.buyfurn.Buyfurn.model.Product;
import com.buyfurn.Buyfurn.service.ProductServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductServices productServices;
	
	 @PostMapping(value = "/admin/addproduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public Product addProduct(@RequestPart("product") String productJson, @RequestPart("imgs") MultipartFile[] images) throws IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Product product = objectMapper.readValue(productJson, Product.class);
	        return productServices.addProduct(product, images);
	}
	 
	 @GetMapping("/getallproducts")
	 public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue = "") String searchKey,
			 @RequestParam(defaultValue = "") String searchCategory ){
		 return productServices.getAllProducts(pageNumber,searchKey,searchCategory);
	 }
	 
	 @GetMapping("/admin/getAllProductsForAdmin")
	 public List<Product> getAllProductsForAdmin(@RequestParam(defaultValue = "0") int pageNumber,@RequestParam(defaultValue = "") String searchKey,
			 @RequestParam(defaultValue = "") String searchCategory ){
		 return productServices.getAllProducts(pageNumber,searchKey,searchCategory);
	 }
	 
	 @GetMapping("/getbyid/{id}")
	 public Product getbyId(@PathVariable Long id) {
		 return productServices.getById(id);
	 }
	 
	 @PostMapping(value = "/admin/updateproduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public Product updateProduct(@RequestPart("product") String prod, @RequestPart(value="img",required = false) MultipartFile[] image) throws IOException {
		 ObjectMapper objectMapper = new ObjectMapper();
	        Product product = objectMapper.readValue(prod, Product.class);
		 return productServices.updateProduct(product,image);
	 }
	 
	 @DeleteMapping("/admin/deletebyid/{id}")
	 public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
	        String response = productServices.deleteById(id);
	        if ("Product Deleted !!".equals(response)) {
	            return new ResponseEntity<>(HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	  }
	 
	 @GetMapping("/user/getproductdetails/{isSingleProductCheckout}/{productId}")
	 public List<Product> getProdctDetails(Principal principal, @PathVariable boolean isSingleProductCheckout,@PathVariable long productId) {
		return productServices.getProductDetails(principal,isSingleProductCheckout, productId);
	 }
	 
	 @GetMapping("/latest")
	    public List<Product> getLatestProducts() {
	        return productServices.getLatestProducts();
	    }
}
