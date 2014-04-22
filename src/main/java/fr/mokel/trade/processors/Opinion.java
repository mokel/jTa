package fr.mokel.trade.processors;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Opinion {
	
	private static Logger logger = Logger.getLogger(Opinion.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  // Instanciation d'un contexte Spring à partir d'un fichier XML dans le classpath
        ApplicationContext ap = new ClassPathXmlApplicationContext("applicationContext.xml");
        Test a = (Test) ap.getBean("testBean");
        a.printTest("GLE.PA");
//
//        // Instanciation d'un contexte Spring à partir d'un fichier sur le disque
//        ApplicationContext ap2 = new FileSystemXmlApplicationContext("c:\\applicationContext.xml");
//
//        // Instanciation d'un BeanFactory à partir d'un fichier xml dans le classpath
//        Resource resource = new ClassPathResource("com/developpez/spring/beans.xml");
//        BeanFactory factory = new XmlBeanFactory(resource);
//
//        // Instanciation d'un BeanFactory à partir d'un fichier xml sur le disque
//        Resource resource2 = new FileSystemResource("c:\\beans.xml");
//        BeanFactory factory2 = new XmlBeanFactory(resource);
        
	}
	
	public static void printList(List toPrint) {
		for (Object object : toPrint) {
			logger.info(object);
		}
	}

}
