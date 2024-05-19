package com.submodule.specification;

import com.submodule.annotation.SearchField;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class MongoQuery<T> {

    private final MongoTemplate mongoTemplate;


    public MongoQuery(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public ResponseGenerator<T> search(Object object, Class<T> clazz, Pageable pageable, Boolean isPageable) throws IllegalAccessException {
        Query query = queryBuilder(object);
        List<T> result = new ArrayList<>();
        ResponseGenerator<T> generator = new ResponseGenerator<>();
        generator.setTotal((long) mongoTemplate.find(query, clazz).size());
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        );
        if (isPageable) {
            result = mongoTemplate.aggregate(agg, object.getClass(), clazz).getMappedResults();
        } else {
            result = mongoTemplate.find(query, clazz);
        }
        generator.setObject(result);
        return generator;

    }


    public Query queryBuilder(Object o) throws IllegalAccessException {
        Criteria criteria = new Criteria();
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            criteria = makeCriteria(field, criteria, o);
        }
        return new Query(criteria);
    }


    private Criteria makeCriteria(Field field, Criteria criteria, Object o) throws IllegalAccessException {
        field.setAccessible(true);
        Annotation[] annotations = field.getAnnotations();
        Object value = field.get(o);
        if (value != null && !value.equals("")) {
            Criteria c = new Criteria();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == SearchField.class) {
                    SearchField annotationField = (SearchField) annotation;
                    switch (annotationField.type()) {
                        case EQUAL_TO -> c = new Criteria(!annotationField.target_field().isEmpty() ? annotationField.target_field() : field.getName()).is(value);
                        case CONTAINS -> c = new Criteria(!annotationField.target_field().isEmpty() ? annotationField.target_field() : field.getName()).alike(Example.of(value));
                        case GREATER_THAN -> c = new Criteria(!annotationField.target_field().isEmpty() ? annotationField.target_field() : field.getName()).gte(value);
                        case LESS_THAN -> {
                            if (value instanceof Date) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime((Date) value);
                                calendar.add(Calendar.DATE, 1);
                                c = new Criteria(!annotationField.target_field().isEmpty() ? annotationField.target_field() : field.getName()).lte(calendar.getTime());
                            } else {
                                c = new Criteria(!annotationField.target_field().isEmpty() ? annotationField.target_field() : field.getName()).lte(value);
                            }
                        }
                    }
                    criteria.andOperator(c);
                }
            }
        }
        return criteria;
    }
}
