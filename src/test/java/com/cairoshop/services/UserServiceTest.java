package com.cairoshop.services;

public class UserServiceTest /*extends BaseCommonServiceTest*/ {

    /*
    * @Autowired
    private UserRepository userRepository;

    public UserServiceImpl() {
        super(SavedDetailedCustomerDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(userRepository);
    }

    @Transactional
    @Override
    public void edit(NewCustomerStateDTO newCustomerStateDTO) {
        int affectedRows = userRepository.update(newCustomerStateDTO.getId(), newCustomerStateDTO.isActive());
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public SavedItemsDTO<SavedBriefCustomerDTO> findAllCustomers(int startPosition, String sortBy, String sortDirection) {
        List<SavedBriefCustomerDTO> page = userRepository.findAllCustomers(startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        int countOfAllCustomers = userRepository.countAllCustomers();
        SavedItemsDTO<SavedBriefCustomerDTO> savedBriefCustomerDTOSavedItemsDTO = new SavedItemsDTO<>();
        savedBriefCustomerDTOSavedItemsDTO.setItems(page);
        savedBriefCustomerDTOSavedItemsDTO.setAllSavedItemsCount(countOfAllCustomers);
        return savedBriefCustomerDTOSavedItemsDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("This user name {0} does not exist", username)));
    }
    * */
}
