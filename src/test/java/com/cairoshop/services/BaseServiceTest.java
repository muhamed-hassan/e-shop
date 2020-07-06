package com.cairoshop.services;

public class BaseServiceTest<NDTO, SDDTO, SBDTO, T> extends BaseCommonServiceTest<SDDTO, SBDTO, T> {

    private Class<T> entityClass;

    public BaseServiceTest(Class<T> entityClass, Class<SDDTO> savedDetailedDtoClass) {
        super(savedDetailedDtoClass);
        this.entityClass = entityClass;
    }

    /*


    @Transactional
    @Override
    public int add(NDTO ndto) {
        Integer id = -1;
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            Method[] dtoMethods = ndto.getClass().getMethods();
            for (Method method : dtoMethods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                    Object valueFromDto = method.invoke(ndto);
                    String setterNameFromEntity = methodName.replace("get", "set");
                    entity.getClass().getMethod(setterNameFromEntity, method.getReturnType()).invoke(entity, valueFromDto);
                }
            }
            entity.getClass().getMethod("setActive", boolean.class).invoke(entity, true);
            entity = getRepository().save(entity);
            id = (Integer) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Transactional
    @Override
    public void removeById(int id) {
        int affectedRows = ((BaseRepository)getRepository()).softDeleteById(id);
        if (affectedRows == 0) {
            throw new DataNotDeletedException();
        }
    }
    * */
}
