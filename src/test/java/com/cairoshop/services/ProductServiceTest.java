package com.cairoshop.services;

public class ProductServiceTest /*extends BaseServiceTest*/ {

    /*
    * @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl() {
        super(Product.class, SavedDetailedProductDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(productRepository);
    }

    @Transactional
    @Override
    public int add(NewProductDTO newProductDTO) {
        Integer id = -1;
        try {
            Product product = new Product();
            product.setName(newProductDTO.getName());
            product.setDescription(newProductDTO.getDescription());
            product.setPrice(newProductDTO.getPrice());
            product.setQuantity(newProductDTO.getQuantity());
            Vendor vendor = new Vendor();
            vendor.setId(newProductDTO.getVendorId());
            product.setVendor(vendor);
            Category category = new Category();
            category.setId(newProductDTO.getCategoryId());
            product.setCategory(category);
            product.setActive(true);
            id = productRepository.save(product).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public byte[] getImage(int id) {
        Optional<SavedImageStream> savedImageStream = productRepository.findImageById(id, SavedImageStream.class);
        if (savedImageStream.isEmpty()) {
            throw new NoResultException();
        }
        return savedImageStream.get().getImage();
    }

    @Transactional
    @Override
    public void edit(SavedDetailedProductDTO savedDetailedProductDTO) {
        int affectedRows = productRepository.update(savedDetailedProductDTO.getId(), savedDetailedProductDTO.getName(),
                                                        savedDetailedProductDTO.getPrice(), savedDetailedProductDTO.getQuantity(),
                                                        savedDetailedProductDTO.getCategoryId(), savedDetailedProductDTO.getVendorId());
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Transactional
    @Override
    public void edit(int id, byte[] image) {
        int affectedRows = productRepository.update(id, image);
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<String> getSortableFields() {
        return Product.SORTABLE_FIELDS;
    }

    @Override
    public SavedItemsDTO<SavedBriefProductDTO> searchByProductName(String name, int startPosition, String sortBy, String sortDirection) {
        List<SavedBriefProductDTO> page = productRepository.search(name, startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        int countOfItemMetSearchCriteria = productRepository.countAllByCriteria(name);
        SavedItemsDTO<SavedBriefProductDTO> savedBriefProductDTOSavedItemsDTO = new SavedItemsDTO<>();
        savedBriefProductDTOSavedItemsDTO.setItems(page);
        savedBriefProductDTOSavedItemsDTO.setAllSavedItemsCount(countOfItemMetSearchCriteria);
        return savedBriefProductDTOSavedItemsDTO;
    }
    * */
}
