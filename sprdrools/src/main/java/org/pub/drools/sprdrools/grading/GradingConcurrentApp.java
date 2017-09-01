/**
 * 
 */
package org.pub.drools.sprdrools.grading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wuhuoxin 2017年8月18日 下午5:58:27
 *
 */
public class GradingConcurrentApp {
	
	private static Logger logger = LoggerFactory.getLogger(GradingConcurrentApp.class);

	public static void main(String[] args) {
		System.setProperty("drools.dialect.mvel.strict", "false");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		StatelessKieSession kieSession = (StatelessKieSession)context.getBean("gradingsession");
		List glist = new ArrayList();
		kieSession.setGlobal("GLIST", glist);
		
		workWithStatelessKieSession(kieSession);
		workWithStatelessKieSession2(kieSession);
	}
	
	public static void workWithStatelessKieSession(StatelessKieSession kieSession){
		ExamScore examScore = new ExamScore();
		examScore.setStuId("stu001");
		examScore.setYuwenScore(80);
		examScore.setShuxueScore(70);
		examScore.setYingyuScore(75);
		
		GradingResult gradingResult = new GradingResult();

		//kieSession.execute(Arrays.asList(new Object[]{examScore}));
		//logger.info("examScore.getLevel():" + examScore.getLevel());
		
		GradingWorker worker1 = new GradingWorker("worker1", kieSession, examScore, gradingResult);
		worker1.start();
	}
	
	public static void workWithStatelessKieSession2(StatelessKieSession kieSession){
		ExamScore examScore = new ExamScore();
		examScore.setStuId("stu002");
		examScore.setYuwenScore(98);
		examScore.setShuxueScore(90);
		examScore.setYingyuScore(95);
		
		GradingResult gradingResult = new GradingResult();

		//kieSession.execute(Arrays.asList(new Object[]{examScore}));
		//logger.info("examScore.getLevel():" + examScore.getLevel());
		
		GradingWorker worker2 = new GradingWorker("worker2", kieSession, examScore, gradingResult);
		worker2.start();
	}
	
	private static class GradingWorker extends Thread {
		
		private String name;
		
		private StatelessKieSession kieSession;
		
		private ExamScore examScore;
		
		private GradingResult gradingResult;
		
		public GradingWorker(String name, StatelessKieSession kieSession, ExamScore examScore, GradingResult gradingResult){
			this.name = name;
			this.kieSession = kieSession;
			this.examScore = examScore;
			this.gradingResult = gradingResult;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			kieSession.execute(Arrays.asList(new Object[]{examScore, gradingResult}));
			logger.info("{} {} {}", name, examScore.getStuId(), gradingResult.getLevel());
			
			List glist = (ArrayList)kieSession.getGlobals().get("GLIST");
			logger.info("glist.size():{}", glist.size());
			logger.info("glist:{}", glist.toString());
		}
		
	}

}
