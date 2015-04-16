package com.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DataUtil {
		
    private static Locale currentLocale;

    public DataUtil() {
        super();
        DataUtil.currentLocale = new Locale("pt", "BR");
    }

    public DataUtil(Locale locale) {
        super();
        DataUtil.currentLocale = locale;
    }
    
    /*
    public static String DataForStringMySQL(Data dataHoraDoDia) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = "";
        try {
            s = df.format(dataHoraDoDia);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    */
    public static String DataForStringXML(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static int MesEmInteiro(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return Integer.parseInt(s);
    }
    
    public static int AnoEmInteiro(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return Integer.parseInt(s);
    }
 
    public static int diasUteisRestantes() {
        int dias = 0;

        Calendar calini = new GregorianCalendar();
        Calendar calfim = new GregorianCalendar();

        calini.setTime(new Date());
        calfim.setTime(ultimoDiaMes());

        while (calini.before(calfim) || calini.equals(calfim)) {
            if ((calini.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                    && (calini.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
                dias += 1; // dias+= -1;
            }
            calini.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dias;
    }

    public static int diasUteis() {
        int dias = 0;

        Calendar calini = new GregorianCalendar();
        Calendar calfim = new GregorianCalendar();

        calini.setTime(primeiroDiaMes());
        calfim.setTime(ultimoDiaMes());

        while (calini.before(calfim) || calini.equals(calfim)) {
            if ((calini.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                    && (calini.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)) {
                dias += 1; // dias+= -1;
            }
            calini.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dias;
    }
    
    // - Pega a data de hoje
    public static String getDateToday(int dateFormat) {
        Date today = new Date();
        DateFormat formatter;
        try {
            formatter = DateFormat.getDateInstance(dateFormat, currentLocale);
            return formatter.format(today);
        } catch (NullPointerException np) {
            // np.printStackTrace();
        }
        return "o tipo " + dateFormat + " não é compativel com a função!";
    }

    public static String Hoje() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = df.format(d);
        return s;
    }

    public static String HojeDataHoraMySQL() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = df.format(d);
        return s;
    }
    
    public static String HojeDataMySQL() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(d);
        return s;
    }
    
    public static String MesAnoString(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
        String s = df.format(data);
        return s;
    }
    
    public static String DiaMesAnoString(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String s = df.format(data);
        return s;
    }
    
    public static String MesAnoStringBarra(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        String s = df.format(data);
        return s;
    }
            
    public static String DiaMesAnoStringBarra(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = df.format(data);
        return s;
    }
        
    public static String DateToStr(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = "  /  /    ";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static String DateToStrHoraMinuto(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        String s = "  :  ";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static Date StrTodate(String pDate) {

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Date dia = null;
        try {
            
            dia = df.parse(pDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dia;
    }

    public static String HoraMinutoAMPM(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("H:mm");
        String s = "  :  ";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static String DataForStringPadrao(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }

    public static String DataHoraForStringPadrao(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static String DataHoraForStringPadraoH(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static String DataHoraForStringMySQL(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
        
    public static String DataHoraForStringMySQLHoraDia(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static String DataForStringMySQL(Date pDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = "";
        try {
            s = df.format(pDate);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return s;
    }
    
    public static Date RetornaHojeData(){
    	Date dt = new Date();
    	return dt;
    }
    
    public static Date DataMySQLHoraDia(String pDate) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");

        Date dia = null;
        try {
            
            dia = df.parse(pDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dia;
    }

    public static Date primeiroDiaMes() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int primeiroDia = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), primeiroDia);
        Date diaDate = cal.getTime();
        String diaString = df.format(diaDate);
        try {
            diaDate = df.parse(diaString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return diaDate;
    }

    public static Date ultimoDiaMes() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        int ultimoDia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), ultimoDia);
        Date diaDate = cal.getTime();
        String diaString = df.format(diaDate);
        try {
            diaDate = df.parse(diaString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return diaDate;
    }

	public static Date StringParaData(String data) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
		java.util.Date dataUtil = df.parse(data);  		
		try {
			return dataUtil;
		} catch (Exception e) {
			return null;
		}	
	}
		
	public static Date StringParaDataHora(String data) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");  
		java.util.Date dataUtil = df.parse(data);  		
		try {
			return dataUtil;
		} catch (Exception e) {
			return null;
		}	
	}
	
	public static List<Date> diasDoMesAno(int mes, int ano){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,mes);
		cal.set(Calendar.YEAR, ano);
		int quantidadeDias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Date> listaDias = new ArrayList<Date>();
		for (int x = 1 ; x <= quantidadeDias ; x++){
			Calendar calendario = Calendar.getInstance();
			calendario.set(Calendar.DATE,x);
			calendario.set(Calendar.MONTH,mes);
			calendario.set(Calendar.YEAR,ano);
			Date hoje = calendario.getTime();
			listaDias.add(hoje);
		}
		return listaDias;
	}
	
	
	
	public static long segundosEntreDatas(Date inicio, Date fim){
		long secIn = inicio.getTime();
		long secFi = fim.getTime();
		long dif = (secFi - secIn) / 1000;
		return dif;
	}
		
	public static double diferencaEmDias(Date dataInicial, Date dataFinal){  
		double result = 0;  
		long diferenca = dataFinal.getTime() - dataInicial.getTime();  
		double diferencaEmDias = (diferenca /1000) / 60 / 60 /24;              //resultado é diferença entre as datas em dias  
		long horasRestantes = (diferenca /1000) / 60 / 60 %24;                 //calcula as horas restantes  
		result = diferencaEmDias + (horasRestantes /24d);                      //transforma as horas restantes em fração de dias  
		return result;  
	}  

}