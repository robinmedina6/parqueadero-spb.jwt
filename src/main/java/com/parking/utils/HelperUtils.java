package com.parking.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;


@Component
public class HelperUtils {

    @Autowired
    private Environment env;


    public static String quitaTilde(String original) {
        //String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        String cadenaNormalize = Normalizer.normalize(original, Normalizer.Form.NFD);
        String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
        return cadenaSinAcentos;

    }

    public static double getDouble(String str) {
        double rta = str.isEmpty() ? 0d : Double.parseDouble(str.toString().replace(",", "."));
        return rta;
    }
    public static short getShort(String str) {
        short rta = str.isEmpty() ? 0 : (short) getDouble(str.toString().replace(",", "."));
        return rta;
    }
    public static Long getLong(String str) {
        Long rta = str.isEmpty() ? 0L : (long) Double.parseDouble(str.toString().replace(",", "."));
        return rta;
    }
    public static int getInt(String str) {
        int rta = str.isEmpty() ? 0 : (int) Double.parseDouble(str.toString().replace(",", "."));
        return rta;
    }

    public static String getNum(String str) {
        String dest = "";
        if (str != null) {
            dest = str.replaceAll("[^0-9]", "");

        }
        return dest;
    }

    private Object SimpleDateFormat(String ddMMyyyy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public String obtenerRutaDeParametro(String key) {
        String path = env.getProperty(key);
        return System.getProperties().getProperty("user.dir")+path ;
    }
    public static String obtenerUserDir() {
        return System.getProperties().getProperty("user.dir") ;
    }

    public static String fechaHoyMysql() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public static String fechaAntMysql(int diasAnt) {
        LocalDateTime ahora = LocalDateTime.now();
        ahora.plusDays(-diasAnt);
        int year = ahora.getYear();
        Formatter fmt = new Formatter();
        fmt.format("%02d", ahora.getMonthValue());
        String mes = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getDayOfMonth());
        String dia = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getHour());
        String hora = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getMinute());
        String minutos = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getSecond());
        String segundos = fmt.toString();
        return year + "-" + mes + "-" + dia;
    }

    public static String fechaHoySiga() {
        LocalDateTime ahora = LocalDateTime.now();
        int year = ahora.getYear();
        Formatter fmt = new Formatter();
        fmt.format("%02d", ahora.getMonthValue());
        String mes = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getDayOfMonth());
        String dia = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getHour());
        String hora = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getMinute());
        String minutos = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getSecond());
        String segundos = fmt.toString();
        return dia + "-" + mes + "-" + year;
    }

    public static String fechaAntSiga(int diasAnt) {
        LocalDateTime ahora = LocalDateTime.now();
        ahora = ahora.minusDays(diasAnt);
        int year = ahora.getYear();
        Formatter fmt = new Formatter();
        fmt.format("%02d", ahora.getMonthValue());
        String mes = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getDayOfMonth());
        String dia = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getHour());
        String hora = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getMinute());
        String minutos = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getSecond());
        String segundos = fmt.toString();
        return dia + "-" + mes + "-" + year;
    }

    public static int horaActual() {
        Calendar diaActual = Calendar.getInstance();
        Date hora = diaActual.getTime();
        return diaActual.get(Calendar.HOUR_OF_DAY);
    }

    public static String obtenerFechaSiga(String fecha) {
        String[] strfecha = fecha.split("-");
        return strfecha[2] + "-" + strfecha[1] + "-" + strfecha[0];
    }

    public static String obtenerFechaMySql(String fecha) {
        String[] strfecha = fecha.split("-");
        return strfecha[0] + "-" + strfecha[1] + "-" + strfecha[2];
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static String obtenerFechaMySql(Date fecha) {
        LocalDateTime ahora = convertToLocalDateTimeViaInstant(fecha);
        int year = ahora.getYear();
        Formatter fmt = new Formatter();
        fmt.format("%02d", ahora.getMonthValue());
        String mes = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getDayOfMonth());
        String dia = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getHour());
        return year + "-" + mes + "-" + dia;
    }

    public static String obtenerFechaSiga(Date fecha) {
        LocalDateTime ahora = convertToLocalDateTimeViaInstant(fecha);
        int year = ahora.getYear();
        Formatter fmt = new Formatter();
        fmt.format("%02d", ahora.getMonthValue());
        String mes = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getDayOfMonth());
        String dia = fmt.toString();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getHour());
        return dia + "-" + mes + "-" + year;
    }

    public static String obtenerDia(){
        LocalDateTime ahora = LocalDateTime.now();
        Formatter fmt = new Formatter();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getDayOfMonth());
        return fmt.toString();
    }

    public static int obtenerYear(){
        LocalDateTime ahora = LocalDateTime.now();
        return ahora.getYear();
    }

    public static String obtenerMes(){
        LocalDateTime ahora = LocalDateTime.now();
        Formatter fmt = new Formatter();
        fmt.format("%02d", ahora.getMonthValue());
        return  fmt.toString();
    }
    public static String obtenerHoras(){
        LocalDateTime ahora = LocalDateTime.now();
        Formatter fmt = new Formatter();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getHour());
        return  fmt.toString();

    }
    public static String obtenerMinutos(){
        LocalDateTime ahora = LocalDateTime.now();
        Formatter fmt = new Formatter();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getMinute());
        return  fmt.toString();

    }
    public static String obtenerSegundos(){
        LocalDateTime ahora = LocalDateTime.now();
        Formatter fmt = new Formatter();
        fmt = new Formatter();
        fmt.format("%02d", ahora.getSecond());
        return  fmt.toString();

    }
    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Calendar restarCalendar(Calendar fechaInicial, Calendar fechaFinal){
        Date fecha =new Date(fechaFinal.getTimeInMillis()- fechaInicial.getTimeInMillis());
        Calendar fechaCalendar=   Calendar.getInstance();
        fechaCalendar.setTime(fecha);
        return fechaCalendar;
    }

    public static LocalDateTime restarLocalDateTime(LocalDateTime fechaInicial, LocalDateTime fechaFinal){
        LocalDateTime actual= fechaFinal.minusSeconds(fechaInicial.getSecond());
        actual= actual.minusMinutes(fechaInicial.getMinute());
        actual= actual.minusHours(fechaInicial.getHour());
        actual=actual.minusDays(fechaInicial.getMinute());
        actual=actual.minusMonths(fechaInicial.getMonthValue());
        actual=actual.minusYears(fechaInicial.getYear());
        return actual;
    }

    private static long getMillisOf(Calendar calendar) {
        Calendar cal = (Calendar) calendar.clone();
        cal.setLenient(true);
        return cal.getTimeInMillis();
    }
    public static Calendar convertStringToCalendar(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm");
        Date date = sdf.parse(fecha);// all done
        //return  sdf.getCalendar();

        String pattern = "yyyy-MM-dd'T'HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(fecha));
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return calendar;
    }
}
