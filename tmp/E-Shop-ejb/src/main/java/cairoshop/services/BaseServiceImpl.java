package cairoshop.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.cairoshop.GlobalLogger;

import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.BaseRepository;
import cairoshop.repositories.specs.Condition;
import cairoshop.repositories.specs.ConditionConnector;
import cairoshop.repositories.specs.QuerySpecs;
import cairoshop.services.interfaces.BaseService;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseServiceImpl<NDTO, SDTO, T> implements BaseService<NDTO, SDTO, T> {

    private BaseRepository repository;
    private Class<NDTO> newDtoClass;
    private Class<SDTO> savedDtoClass;
    private Class<T> entityClass;

    public BaseServiceImpl(Class<NDTO> newDtoClass, Class<SDTO> savedDtoClass, Class<T> entityClass) {
        this.newDtoClass = newDtoClass;
        this.savedDtoClass = savedDtoClass;
        this.entityClass = entityClass;
    }
    
    protected void setRepo(BaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer add(NDTO newDto) {
        Integer id = -1;
        try {
            T entity = entityClass.newInstance();
            Method[] dtoMethods = newDto.getClass().getMethods();
            for (Method method : dtoMethods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                    Object valueFromDto = method.invoke(newDto);
                    String setterNameFromEntity = methodName.replace("get", "set");                    
                    entity.getClass().getMethod(setterNameFromEntity, method.getReturnType()).invoke(entity, valueFromDto);
                }
            }
            entity.getClass().getMethod("setActive", boolean.class).invoke(entity, true);
            id = repository.add(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public void edit(Map<String, Object> fields) {
        try {
            repository.update(fields);
        } catch (ModificationException me) {
            throw new RuntimeException(me);
        }
    }

    @Override
    public void remove(int id) {
        try {
            repository.remove(id);
        } catch (DeletionException de) {
            throw new RuntimeException(de);
        }
    }

    @Override
    public SDTO getById(int id) {
        
        SDTO savedDto = null;
        try {
             Condition condition = new Condition("id", ConditionConnector.EQUAL,id);
             QuerySpecs querySpecs = new QuerySpecs()
                                            .addPredicate(condition)
                                            .addPredicate(new Condition("active", ConditionConnector.EQUAL,true));
            Map<String, Class<?>> fields = new HashMap<>();
            getFields(savedDtoClass, fields);
            for (Map.Entry<String, Class<?>> field : fields.entrySet()) {
                querySpecs.addField(field.getKey());
            }
        
            Object[] record = repository.find(querySpecs);
            savedDto = savedDtoClass.newInstance();
            List<String> selectedFields = querySpecs.getFields();
            for (int i = 0; i < record.length; i++) {                
                char capitalizedFirstChar = Character.toUpperCase(selectedFields.get(i).charAt(0));                
                String setterMethodName = "set" + capitalizedFirstChar + selectedFields.get(i).substring(1);           
                savedDto.getClass().getMethod(setterMethodName, fields.get(selectedFields.get(i))).invoke(savedDto, record[i]);                
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return savedDto;
    }
    
    private void getFields(Class<?> clazz, Map<String, Class<?>> fields) {
        if (clazz == Object.class) return;

        Field[] declaredFieldsOfCurrentClass = clazz.getDeclaredFields();
        for (Field field : declaredFieldsOfCurrentClass) {            
            fields.put(field.getName(), field.getType());
        }
        Class<?> superClass = clazz.getSuperclass(); 
        getFields(superClass, fields);
    }

    @Override
    public List<SDTO> findAll(QuerySpecs querySpecs) {
        return null;
    }

    @Override
    public List<SDTO> findAll(QuerySpecs querySpecs, int startPosition) {
        return null;
    }

    @Override
    public int getCount(QuerySpecs querySpecs) {
        return 0;
    }

}
