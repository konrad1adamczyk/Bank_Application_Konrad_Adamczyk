package com.luxoft.bankapp.service;

import com.luxoft.bankapp.database.NoDB;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by KAdamczyk on 2016-01-18.
 */
public class TestService {
    /**
     * This method should analyze the fields o1 and o2.
     * It should compare all the fields with the help of equals,
     * except for those fields that are marked with an annotation
     *  @NoDB
     * And return true, if all the fields are equal.
     * Also it should be able to compare the collections.
     */
    public static boolean isEquals(Object o1, Object o2) {
        Field[] fieldsObject1 = o1.getClass().getDeclaredFields();
        Field[] fieldsObject2 = o2.getClass().getDeclaredFields();

        for(int i = 0; i < fieldsObject1.length; i++) {
            fieldsObject1[i].setAccessible(true);
            Annotation[] annotations = fieldsObject1[i].getAnnotations();
            for(Annotation a : annotations) {
                if(a instanceof NoDB) {
                    fieldsObject1[i] = null;
                }
            }
        }
        for(int i = 0; i < fieldsObject2.length; i++) {
            fieldsObject2[i].setAccessible(true);
            Annotation[] annotations = fieldsObject2[i].getAnnotations();
            for(Annotation a : annotations) {
                if(a instanceof NoDB) {
                    fieldsObject2[i] = null;
                }
            }
        }
        for(int i = 0; i < fieldsObject1.length; i++) {
            try {
                if(!fieldsObject1[i].get(o1).equals(fieldsObject2[i].get(o2)))
                    return false;
            } catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
                continue;
            }
        }

        return true;

    }
}