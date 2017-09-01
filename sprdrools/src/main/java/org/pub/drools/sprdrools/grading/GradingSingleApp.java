/**
 * 
 */
package org.pub.drools.sprdrools.grading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wuhuoxin 2017年8月18日 下午5:58:27
 *
 */
public class GradingSingleApp {
	
	private static Logger logger = LoggerFactory.getLogger(GradingSingleApp.class);

	public static void main(String[] args) {
		System.setProperty("drools.dateformat", "yyyy-MM-dd");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		StatelessKieSession kieSession = (StatelessKieSession)context.getBean("gradingsession");
		KieSession kieSessionStateful = (KieSession)context.getBean("gradingsession_stateful");
		
		//List glist = new ArrayList();
		//kieSession.setGlobal("GLIST", glist);
		//kieSessionStateful.setGlobal("GLIST", glist);
		
		workWithStatelessKieSession(kieSession);
		//workWithStatefulKieSession(kieSessionStateful);

	}
	
	public static void workWithStatelessKieSession(StatelessKieSession kieSession){
		ExamScore examScore = new ExamScore();
		examScore.setStuId("stu001");
		examScore.setYuwenScore(90);
		examScore.setShuxueScore(80);
		examScore.setYingyuScore(85);
		
		GradingResult gradingResult = new GradingResult();

		kieSession.execute(Arrays.asList(new Object[]{examScore, gradingResult}));
		logger.info("{} {}", examScore.getStuId(), gradingResult.getLevel());
	}
	
	public static void workWithStatefulKieSession(KieSession kieSession){
		try {
			ExamScore examScore = new ExamScore();
			examScore.setStuId("stu001");
			examScore.setYuwenScore(90);
			examScore.setShuxueScore(80);
			examScore.setYingyuScore(85);
			
			GradingResult gradingResult = new GradingResult();
			
			FactHandle examScoreHandler = kieSession.insert(examScore);
			kieSession.insert(gradingResult);
			int ruleFiredCount = kieSession.fireAllRules();
			logger.info("trigger {} rules", ruleFiredCount);
			logger.info("{} {}", examScore.getStuId(), gradingResult.getLevel());
			
			examScore.setShuxueScore(90);
			examScore.setYingyuScore(95);
			kieSession.update(examScoreHandler, examScore);
			ruleFiredCount = kieSession.fireAllRules();
			logger.info("trigger {} rules", ruleFiredCount);
			logger.info("{} {}", examScore.getStuId(), gradingResult.getLevel());			
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (kieSession != null) {
				kieSession.dispose();
			}
		}		
	}
	

}
