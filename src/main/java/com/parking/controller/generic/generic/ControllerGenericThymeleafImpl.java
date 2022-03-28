package com.parking.controller.generic.generic;


import com.parking.controller.generic.IControllerGenericThymeleaf;
import com.parking.modelo.Empleado;
import com.parking.modelo.generic.base.BaseEntity;
import com.parking.modelo.generic.base.FieldThymeleaf;
import com.parking.service.generics.generic.IServiceGeneric;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
@ResponseBody
public class ControllerGenericThymeleafImpl<T extends BaseEntity> implements IControllerGenericThymeleaf<T> {

    @Autowired
    private IServiceGeneric<T> genericService;

    @Setter
    private T newEntity ;

    Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    @GetMapping("/new")
    public ModelAndView nuevo() {
        try {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("gencrud/nuevo");
            log.info("imprimiendo lista de propiedades:" + newEntity.getClass().getSimpleName());
            log.info("imprimiendo lista de propiedades:");
            mv.addObject("listaCampos", this.getProperties(newEntity));
            mv.addObject("formAction", newEntity.getClass().getSimpleName().toLowerCase());
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/detalle/{id}")
    public ModelAndView detalle(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("gencrud/detalle");
        try {
            T entity = genericService.findById(id);
            mv.addObject("listaCampos", this.getProperties(entity));
            mv.addObject("mapdata", getFiedlsObject(entity));
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("error", e.getMessage());
            return mv;
        }
    }

    @Override
    @PostMapping("/save")
    public ResponseEntity<GenericMessage> save(T entity) {
        GenericMessage gm = new GenericMessage();
        gm.setStatus(3);
        try {
            T saved = genericService.save(entity);
            log.info("ingeso a guardar" + saved.toString());
            if (saved == null) {
                gm.setError("fallo al guardar revisar server");
                gm.setStatus(3);
                return ResponseEntity.ok(gm);
            } else {
                gm.setMessage("guardado");
                gm.setStatus(2);
                gm.setUrlRedirect("detalle/" + saved.getId() + "");
                return ResponseEntity.ok(gm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            gm.setError(e.getMessage());
            return ResponseEntity.ok(gm);
        }
    }

    @Override
    @GetMapping("/lista")
    public ModelAndView lista(T entity) {
        try {
            log.info("ingreso a lisa");
            ModelAndView mv = new ModelAndView("gencrud/lista");
            mv.addObject("listaCampos", this.getProperties(entity));
            mv.addObject("objects", genericService.findAll());
            return mv;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @GetMapping
    public ResponseEntity<T> findAll() {
        try {
            return new ResponseEntity(genericService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Error al Buscar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            genericService.delete(id);
            return new ResponseEntity("se elimino de manera satisfactoria!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("error al eliminar!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public List<FieldThymeleaf> getProperties(T entity) throws Exception {
            List<FieldThymeleaf> fieldThymeleafList = new ArrayList<>();
          // Class _class = Class.forName(clase.getCanonicalName());
           // Field properties[] = _class.getDeclaredFields();
            //fieldThymeleafList.add(getIdFieldThymeleaf());

            List<Field> fieldList = getListFields(entity);
            for (int i = 0; i < fieldList.size(); i++) {
                FieldThymeleaf field = getFieldThymeleaf(fieldList.get(i));
                System.out.println(field.getName() + " : " + field.getType() + " : " + field.getType());
                fieldThymeleafList.add(field);
            }
            return fieldThymeleafList;
    }
    //obtiene la lista de los campos de la clase y superclases
    public List<Field> getListFields(T entity){
        List<Field> fieldList = new ArrayList<>();

        for (Class<?> c = entity.getClass(); c != null;  c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            //for (Field classField : fields) {//los toma en orden de la clase
            //    fieldList.add(classField);
            //}
            for (int i = fields.length-1;i>=0;i--){
                fieldList.add(fields[i]);
            }
        }
        Collections.reverse(fieldList);
        return  fieldList;
    }
    //obtiene la lista o hash de valores
    public Map<String, String> getFiedlsObject(T entity) throws Exception {
        HashMap <String, String> stringMap = new HashMap <String, String> ();
        List<Field> fieldList= getListFields(entity);
        List<String> fieldThymeleafList = new ArrayList<>();

        for (int i = 0; i < fieldList.size(); i++) {
            stringMap.put(fieldList.get(i).getName(),runGetter(fieldList.get(i), entity).toString());
        }
        return stringMap;
    }

    public Object runGetter(Field field, T o) {
        // MZ: Find the correct method
        for (Method method : getMethods(o.getClass())) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        return method.invoke(o);
                    } catch (IllegalAccessException e) {
                        log.error("Could not determine method: " + method.getName());
                    } catch (InvocationTargetException e) {
                        log.error("Could not determine method: " + method.getName());
                    }
                }
            }
        }
        return null;
    }
    // convirte el tipo Field a fieldThymelaf es decir para el front html
    public FieldThymeleaf getFieldThymeleaf(Field field) {
        HashMap obMap = new HashMap<String, String>();
        obMap.put("String", "text");
        obMap.put("Long", "numeric");
        obMap.put("int", "numeric");
        FieldThymeleaf fieldThymeleaf = FieldThymeleaf.builder()
                .name(field.getName())
                .type(obMap.get(field.getType().getSimpleName()).toString())
                .build();
        if (fieldThymeleaf.getName().toLowerCase().equals("email")) {
            fieldThymeleaf.setType("email");
        }
        return fieldThymeleaf;
    }

    public FieldThymeleaf getIdFieldThymeleaf() {
        FieldThymeleaf fieldThymeleaf = FieldThymeleaf.builder()
                .name("id")
                .type("numeric")
                .build();
        return fieldThymeleaf;
    }

    public static Stack<Method> getMethods(Class type) {
        // MZ: Optionally, for performance reasons, cache the (non segmented) methods per type in a static map
        // MZ: this is just an example, and isn't threadsafe
        //if (classMethodCache.containsKey(type))
        //{
        //  return classMethodCache.get(type);
        //}

        Stack<Method> result = new Stack<Method>();
        try {
            for (Class<?> c = type; c != null; c = c.getSuperclass()) {
                Method[] methods = c.getDeclaredMethods();
                for (Method method : methods) {
                    result.add(method);
                }
            }
        } catch (Exception e) {
            // MZ: Add your own logger instance here
            // Logger.error("Could not fetch object methods", e);
        }
        // MZ: Add to caching map
        // classMethodCache.put(type, result);
        return result;
    }

}
