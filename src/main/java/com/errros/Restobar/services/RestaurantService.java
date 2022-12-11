package com.errros.Restobar.services;


import com.errros.Restobar.entities.*;
import com.errros.Restobar.models.*;
import com.errros.Restobar.repositories.*;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CashierRepository cashierRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private Sys_AdminRepository sys_adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TvaRepository tvaRepository;
    @Autowired
   private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccompanimentRepository accompanimentRepository;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FileService fileService;


    public Restaurant createRestaurant(RestaurantRequest restaurantRequest, MultipartFile img) {

        var sysadmin = sys_adminRepository.findAll().get(0);
        Restaurant restaurant = new Restaurant(restaurantRequest);
        restaurant.addSys_admin(sysadmin);
         restaurant = restaurantRepository.save(restaurant);
        //Save the img if it exists
        if(Objects.nonNull(img)){

            var filename = fileService.generateFileName(img,FileType.IMG_RESTAURANT,restaurant);

            var path = fileService.save(img,filename, FileType.IMG_RESTAURANT);
            //persist image with name = restaurantname
            Image image = new Image(filename,path.toString(),restaurant);
            image = imageRepository.save(image);

            //update the category with the saved image
            restaurant.setImage(image);
              restaurant = restaurantRepository.save(restaurant);
        }
         return restaurant;


    }

    public void deleteRestaurant(Long id) {
     restaurantRepository.deleteById(id);
    }




    public Restaurant addCashier(Long id, UserRequest cashierRequest) {
        Cashier cashier = new Cashier(cashierRequest);
        cashier.setRole(UserRole.CASHIER);
        cashier.setPassword(passwordEncoder.encode(cashier.getPassword()));

        Restaurant restaurant = restaurantRepository.getById(id);


            restaurant.addCashier(cashier);
           return restaurantRepository.save(restaurant);

        }




    public Restaurant addOwner(Long id, UserRequest ownerRequest) {
        Owner owner = new Owner(ownerRequest);
        owner.setRole(UserRole.OWNER);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));

        Restaurant restaurant = restaurantRepository.getById(id);
           restaurant.addOwner(owner);

           return restaurantRepository.save(restaurant);

       }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public void deleteOwner(Long idRestaurant, Long idOwner) {
    var restaurant = restaurantRepository.getById(idRestaurant);
    Owner owner = ownerRepository.getById(idOwner);
        restaurant.removeOwner(owner);
        restaurantRepository.save(restaurant);
    }


    public void deleteCashier(Long idRestaurant, Long idCashier) {

            var restaurant = restaurantRepository.getById(idRestaurant);
            var cashier = cashierRepository.getById(idCashier);
            restaurant.removeCashier(cashier);
            restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurant(Long idRestaurant) {
                      Restaurant restaurant = restaurantRepository.getById(idRestaurant);
      return restaurant;
    }

    public Restaurant updateRestaurant(Long idRestaurant, RestaurantRequest restaurantRequest , MultipartFile img) {

        var persistedRestaurant = restaurantRepository.getById(idRestaurant);

        persistedRestaurant.setName(restaurantRequest.getName());
        persistedRestaurant.setPhoneNumber(restaurantRequest.getPhoneNumber());
        persistedRestaurant.setAddress(restaurantRequest.getAddress());

        persistedRestaurant.setImage(null);
        imageRepository.deleteByRestaurant(persistedRestaurant);
        //TODO delete the image physically too
        if(Objects.nonNull(img)){

            var filename = fileService.generateFileName(img,FileType.IMG_RESTAURANT,persistedRestaurant);

            var path = fileService.save(img,filename, FileType.IMG_RESTAURANT);
            //persist image with name = restaurantname
            Image image = new Image(filename,path.toString(),persistedRestaurant);
            image = imageRepository.save(image);

            //update the category with the saved image
            persistedRestaurant.setImage(image);
           persistedRestaurant = restaurantRepository.save(persistedRestaurant);
        }
        return persistedRestaurant;

    }

    public List<Category> getAllCategories(Long idRestaurant){
        return restaurantRepository.getById(idRestaurant).getCategories();
    }
    public Category createCategory(Long idRestaurant, CategoryRequest categoryRequest, MultipartFile img) {

       Restaurant restaurant = restaurantRepository.getById(idRestaurant);

       //retrive the tva1,tva2 with ids in the categoryRequest
        Tva tva1 = tvaRepository.getById(categoryRequest.getTvaOnTableId());
        Tva tva2 = tvaRepository.getById(categoryRequest.getTvaTakenAwayId());


        //are tva1,tva2  of this restaurant
        if(restaurant.getTvas().containsAll(List.of(tva1,tva2))) {

        Category category = new Category(categoryRequest);
         category.addTva(tva1);
         category.addTva(tva2);

        restaurant.addCategory(category);

         category = categoryRepository.save(category);

         //Save the img if it exists
        if(Objects.nonNull(img)){

        var filename = fileService.generateFileName(img,FileType.IMG_CATEGORY,category);

         var path = fileService.save(img,filename, FileType.IMG_CATEGORY);
         //persist image with name = restaurantname-categoryname
         Image image = new Image(String.join("-",restaurant.getName(),"Categorie",category.getName()),path.toString(),category);
         image = imageRepository.save(image);

         //update the category with the saved image
        category.setImage(image);
        }

        return categoryRepository.save(category);


        }else {
        throw new OpenApiResourceNotFoundException("This restaurant doesn't have  one or the two tvas you provided");
    }
    }

    public Category updateCategory(Long idRestaurant, Long idCategory, CategoryRequest categoryRequest, MultipartFile img) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);
        //check if the retrieved category is for the given restaurant
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }

        //retrive the tva1,tva2 with ids in the categoryRequest
        Tva tva1 = tvaRepository.getById(categoryRequest.getTvaOnTableId());
        Tva tva2 = tvaRepository.getById(categoryRequest.getTvaTakenAwayId());


        //are tva1,tva2  of this restaurant
        if(restaurant.getTvas().containsAll(List.of(tva1,tva2))) {

            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            category.clearTvas();

            category.addTva(tva1);
            category.addTva(tva2);

            category.setImage(null);


            //delete preexisting image
            imageRepository.deleteByCategory(category);
            //TODO remove physically the image
            //Save the img if the user has provided it
            if(Objects.nonNull(img)){

                var filename = fileService.generateFileName(img,FileType.IMG_CATEGORY,category);

                var path = fileService.save(img,filename, FileType.IMG_CATEGORY);
                //persist image with name = restaurantname-categoryname
                Image image = new Image(String.join("-",restaurant.getName(),"Categorie",category.getName()),path.toString(),category);
                image = imageRepository.save(image);

                //update the category with the saved image
                category.setImage(image);
            }

            return categoryRepository.save(category);

        }else {
            throw new OpenApiResourceNotFoundException("This restaurant doesn't have  one or the two tvas you provided");
        }


    }

    public Category getCategory(Long idRestaurant, Long idCategory){
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);
        //check if the retrieved category is for the given restaurant
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }
        return category;
    }


    //TODO cleaning the files after deleting the category

    public void deleteCategory(Long idRestaurant,Long idCategory){
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        System.out.println(restaurant);
        Category category = categoryRepository.getById(idCategory);
        System.out.println(category);
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no such category in this restaurant!");
        }

        restaurant.removeCategory(category);

        categoryRepository.deleteById(category.getId());



    }



    public SubCategory createSubCategory(Long idRestaurant, Long idCategory, String subCategoryName, MultipartFile img) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);

        if(!category.getRestaurant().equals(restaurant)) {
         throw new OpenApiResourceNotFoundException("There's no such a category for this restaurant!");
        }

        SubCategory subCategory = new SubCategory(subCategoryName);

        category.addSubCategory(subCategory);

        subCategory = subCategoryRepository.save(subCategory);

            //Save the img if it exists
            if(Objects.nonNull(img)){

                var filename = fileService.generateFileName(img,FileType.IMG_SUBCATEGORY,subCategory);

                var path = fileService.save(img,filename, FileType.IMG_SUBCATEGORY);
                //persist image with name = restaurantname-categoryname
                Image image = new Image(String.join("-",restaurant.getName(),"SubCategorie",subCategory.getName()),path.toString(),subCategory);
                image = imageRepository.save(image);

                //update the category with the saved image
                subCategory.setImage(image);
            }

            return subCategoryRepository.save(subCategory);


        }

    public void deleteSubCategory(Long idRestaurant, Long idCategory, Long idSubCategory) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);

        Category category = categoryRepository.getById(idCategory);

        SubCategory subCategory = subCategoryRepository.getById(idSubCategory);


        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no such category in this restaurant!");
        }

        if(!category.getSubCategories().contains(subCategory)){
            throw new OpenApiResourceNotFoundException("There's no such subcategory in this category!");
        }

        category.removeSubCategory(subCategory);
        subCategoryRepository.deleteById(subCategory.getId());


    }

    public SubCategory updateSubCategory(Long idRestaurant , Long idCategory , Long idSubCategory ,String subCategoryName, MultipartFile img ){

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);
        SubCategory subCategory = subCategoryRepository.getById(idSubCategory);

        //check if the retrieved category is for the given restaurant
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }
       //check if the retrieved subcategory if for the given category
        if(!category.getSubCategories().contains(subCategory)){
            throw new OpenApiResourceNotFoundException("There's no such subcategory in this category!");
        }


            subCategory.setName(subCategoryName);
            subCategory.setImage(null);
            //delete preexisting image
            imageRepository.deleteBySubCategory(subCategory);
            //TODO remove physically the image
            //Save the img if the user has provided it
            if(Objects.nonNull(img)){

                var filename = fileService.generateFileName(img,FileType.IMG_SUBCATEGORY,subCategory);

                var path = fileService.save(img,filename, FileType.IMG_SUBCATEGORY);
                //persist image with name = restaurantname-categoryname
                Image image = new Image(String.join("-",restaurant.getName(),"SubCategorie",subCategory.getName()),path.toString(),subCategory);
                image = imageRepository.save(image);

                //update the category with the saved image
                subCategory.setImage(image);
            }
            return subCategoryRepository.save(subCategory);

    }

    public SubCategory getSubCategory(Long idRestaurant , Long idCategory , Long idSubCategory){
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);
        SubCategory subCategory = subCategoryRepository.getById(idSubCategory);

        //check if the retrieved category is for the given restaurant
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }
        //check if the retrieved subcategory if for the given category
        if(!category.getSubCategories().contains(subCategory)){
            throw new OpenApiResourceNotFoundException("There's no such subcategory in this category!");
        }
        return subCategory;
    }


    public List<Tva> getAllTva(Long idRestaurant){
        return restaurantRepository.getById(idRestaurant).getTvas();
    }
    public Tva createTva(Long idRestaurant, String tvaName, Integer tvaValue) {
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Tva tva = new Tva(tvaName,tvaValue);
        tva.setRestaurant(restaurant);
        return tvaRepository.save(tva);

    }
    public void deleteTva(Long idRestaurant, Long tvaId) {
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
       Tva tva = tvaRepository.getById(tvaId);
       if(restaurant.getTvas().contains(tva)){
           tvaRepository.deleteById(tvaId);
       }else {
           throw new OpenApiResourceNotFoundException(String.format("There's no TVA With id=%d in restaurant with id=%d",tvaId,idRestaurant));
       }

    }



    public Product getProduct(Long idRestaurant, Long idProduct) {
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Product product = productRepository.getById(idProduct);


        //check if the retrieved product is for the given restaurant
        if (Objects.nonNull(product.getCategory()) && !product.getCategory().getRestaurant().equals(restaurant) ||
                Objects.nonNull(product.getSubCategory()) && !product.getSubCategory().getCategory().getRestaurant().equals(restaurant)
        ) {
            throw new OpenApiResourceNotFoundException("There's no such product with such an id in this restaurant");

        }

        return product;

    }

    public Product createProductInsideCategory(Long idRestaurant, Long idCategory, ProductRequest productRequest, MultipartFile img) {


        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);

        //check if the retrieved category is for the given restaurant
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }

        Product product = new Product(productRequest);
        category.addProduct(product);
        product = productRepository.save(product);

        //Save the img if it exists
        if(Objects.nonNull(img)){

            var filename = fileService.generateFileName(img,FileType.IMG_PRODUCT,product);

            var path = fileService.save(img,filename, FileType.IMG_PRODUCT);
            //persist image with name = restaurantname-categoryname
            Image image = new Image(String.join("-",restaurant.getName(),"Product",product.getName()),path.toString(),product);
            image = imageRepository.save(image);

            //update the product with the saved image
            product.setImage(image);
        }
        return productRepository.save(product);

    }







    public Product createProductInsideSubCategory(Long idRestaurant, Long idCategory, Long idSubCategory, ProductRequest productRequest, MultipartFile img) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Category category = categoryRepository.getById(idCategory);
        SubCategory subCategory = subCategoryRepository.getById(idSubCategory);

        //check if the retrieved category is for the given restaurant
        if(!restaurant.getCategories().contains(category)){
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }
        //check if the retrieved subcategory if for the given category
        if(!category.getSubCategories().contains(subCategory)){
            throw new OpenApiResourceNotFoundException("There's no such subcategory in this category!");
        }

        Product product = new Product(productRequest);
        subCategory.addProduct(product);
        product = productRepository.save(product);

        //Save the img if it exists
        if(Objects.nonNull(img)){

            var filename = fileService.generateFileName(img,FileType.IMG_PRODUCT,product);

            var path = fileService.save(img,filename, FileType.IMG_PRODUCT);
            //persist image with name = restaurantname-categoryname
            Image image = new Image(String.join("-",restaurant.getName(),"Product",product.getName()),path.toString(),product);
            image = imageRepository.save(image);
            //update the product with the saved image
            product.setImage(image);
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long idRestaurant, Long idProduct) {
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Product product = productRepository.getById(idProduct);

        //check if the retrieved product is for the given restaurant
        if (Objects.nonNull(product.getCategory()) && !product.getCategory().getRestaurant().equals(restaurant) ||
                Objects.nonNull(product.getSubCategory()) && !product.getSubCategory().getCategory().getRestaurant().equals(restaurant)
        ){
            throw new OpenApiResourceNotFoundException("There's no such product with such an id in this restaurant");
        }


        //a product either belongs to a category or a subcategory , delete the product either from the category or the subcategory he belongs to
        Optional.ofNullable(product.getCategory()).ifPresentOrElse(category -> category.removeProduct(product), new Runnable() {
            @Override
            public void run() {
                product.getSubCategory().removeProduct(product);
            }
        });

        productRepository.delete(product);

    }

    public Product updateProduct(Long idRestaurant, Long idProduct, ProductRequest productRequest, MultipartFile img) {
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Product product = productRepository.getById(idProduct);

        //check if the retrieved product is for the given restaurant
        if (Objects.nonNull(product.getCategory()) && !product.getCategory().getRestaurant().equals(restaurant) ||
                Objects.nonNull(product.getSubCategory()) && !product.getSubCategory().getCategory().getRestaurant().equals(restaurant)
        ){
            throw new OpenApiResourceNotFoundException("There's no such product with such an id in this restaurant");
        }


        product.setName(productRequest.getName());
        product.setPriceOnTable(productRequest.getPriceOnTable());
        product.setPriceTakenAway(productRequest.getPriceTakenAway());
        product.setAllowDiscount(productRequest.getAllowDiscount());
        product.setQtyStock(productRequest.getQtyStock());
        product.setBuyingPrice(productRequest.getBuyingPrice());
        product.setImage(null);
        imageRepository.deleteByProduct(product);

        //Save the img if it exists
        if(Objects.nonNull(img)){

            var filename = fileService.generateFileName(img,FileType.IMG_PRODUCT,product);

            var path = fileService.save(img,filename, FileType.IMG_PRODUCT);
            //persist image with name = restaurantname-categoryname
            Image image = new Image(String.join("-",restaurant.getName(),"Product",product.getName()),path.toString(),product);
            image = imageRepository.save(image);
            //update the product with the saved image
            product.setImage(image);
        }
        return productRepository.save(product);




    }



    public Accompaniment createAccompaniment(Long idRestaurant,Long idProduct , Long idCategory , AccompanimentType type , Integer nbr) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Product product = productRepository.getById(idProduct);
        Category category = categoryRepository.getById(idCategory);

        //check if the retrieved product is for the given restaurant
        if (Objects.nonNull(product.getCategory()) && !product.getCategory().getRestaurant().equals(restaurant) ||
                Objects.nonNull(product.getSubCategory()) && !product.getSubCategory().getCategory().getRestaurant().equals(restaurant)
        ) {
            throw new OpenApiResourceNotFoundException("There's no such product with such an id in this restaurant");

        }

        //check if the retrieved category is for the given restaurant
        if (!restaurant.getCategories().contains(category)) {
            throw new OpenApiResourceNotFoundException("There's no category with such an id in this restaurant");
        }

       Accompaniment accompaniment = new Accompaniment(type,category);
        if(type == AccompanimentType.MANY_INCLUDED_IN_PRICE || type == AccompanimentType.MANY_NOT_INCLUDED_IN_PRICE){
            accompaniment.setNbr(nbr);
        }

        product.addAccompaniment(accompaniment);
        category.addAccompaniment(accompaniment);


       return accompanimentRepository.save(accompaniment);
        }

        public void deleteAccompaniment(Long idRestaurant,Long idProduct , Long idAccompaniment) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Product product = productRepository.getById(idProduct);
        Accompaniment accompaniment = accompanimentRepository.getById(idAccompaniment);
        //check if the retrieved product is for the given restaurant
        if (Objects.nonNull(product.getCategory()) && !product.getCategory().getRestaurant().equals(restaurant) ||
                Objects.nonNull(product.getSubCategory()) && !product.getSubCategory().getCategory().getRestaurant().equals(restaurant)
        ) {
            throw new OpenApiResourceNotFoundException("There's no such product with such an id in this restaurant");

        }

        if(!accompaniment.getCategory().getRestaurant().equals(restaurant)){
            throw new OpenApiResourceNotFoundException("There's no accompaniment with such an id in this restaurant");
        }


        accompaniment.getCategory().removeAccompaniment(accompaniment);
        accompaniment.getProduct().removeAccompaniment(accompaniment);
        accompanimentRepository.deleteById(accompaniment.getId());

        }

    public Supplier addSupplierOfProduct(Long idRestaurant,Long idProduct,Long idSupplier){

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Product product = productRepository.getById(idProduct);
        Supplier supplier = supplierRepository.getById(idSupplier);

        //check if the retrieved product is for the given restaurant
        if (Objects.nonNull(product.getCategory()) && !product.getCategory().getRestaurant().equals(restaurant) ||
                Objects.nonNull(product.getSubCategory()) && !product.getSubCategory().getCategory().getRestaurant().equals(restaurant)
        ){
            throw new OpenApiResourceNotFoundException("There's no such product with such an id in this restaurant");
        }
        //check if the supplier is of the given restaurant
        if(!supplier.getRestaurant().equals(restaurant)){
            throw new OpenApiResourceNotFoundException("There's no such supplier in this restaurant");
        }
        supplier.addProduct(product);
        return supplierRepository.save(supplier);

    }

    public Supplier createSupplier(Long idRestaurant, SupplierRequest supplierRequest) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Supplier supplier = new Supplier(supplierRequest);
        restaurant.addSupplier(supplier);
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long idRestaurant, Long idSupplier, SupplierRequest supplierRequest) {

        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Supplier supplier = supplierRepository.getById(idSupplier);

        supplier.setName(supplierRequest.getName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setCity(supplierRequest.getCity());
        supplier.setPhoneNumber1(supplierRequest.getPhoneNumber1());
        supplier.setPhoneNumber2(supplierRequest.getPhoneNumber2());

    return supplierRepository.save(supplier);

    }

    public void deleteSupplier(Long idRestaurant, Long idSupplier) {
        Supplier supplier = supplierRepository.getById(idSupplier);
       supplier.getProducts().forEach(product -> supplier.removeProduct(product));
       supplier.getRestaurant().removeSupplier(supplier);
    supplierRepository.delete(supplier);
    }

    public List<Supplier> getAllSuppliers(Long idRestaurant){
        return supplierRepository.findByRestaurant(restaurantRepository.getById(idRestaurant));
    }
    public List<Client> getAllClients(Long idRestaurant){
        return clientRepository.findByRestaurant(restaurantRepository.getById(idRestaurant));
    }


    public Client createClient(Long idRestaurant, ClientRequest clientRequest) {
        Restaurant restaurant = restaurantRepository.getById(idRestaurant);
        Client client = new Client(clientRequest);
        restaurant.addClient(client);
       return clientRepository.save(client);
    }

    public Client updateClient(Long idRestaurant, Long idClient, ClientRequest clientRequest) {
        Client client = clientRepository.getById(idClient);
        client.setFirstname(clientRequest.getFirstname());
        client.setSecondname(clientRequest.getSecondname());
        client.setAddress(clientRequest.getAddress());
        client.setCity(clientRequest.getCity());
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setPermanentDiscount(clientRequest.getPermanentDiscount());
return clientRepository.save(client);
    }


    public void deleteClient(Long idRestaurant, Long idClient) {
      Client client = clientRepository.getById(idClient);
      client.getRestaurant().removeClient(client);
      clientRepository.delete(client);
    }

}
