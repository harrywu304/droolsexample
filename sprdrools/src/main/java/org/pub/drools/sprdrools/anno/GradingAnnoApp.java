/**
 * 
 */
package org.pub.drools.sprdrools.anno;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.pub.drools.sprdrools.grading.ExamScore;
import org.pub.drools.sprdrools.grading.GradingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wuhuoxin 2017年8月30日 下午2:54:09
 *
 */
public class GradingAnnoApp {
	
	private static Logger logger = LoggerFactory.getLogger(GradingAnnoApp.class);

	public static void main(String[] args) {
	    ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	    StatelessKieSession kieSession = ctx.getBean(StatelessKieSession.class);
	    kieSession = ctx.getBean(StatelessKieSession.class);
	    ctx.getBean(InternalKnowledgeBase.class);
	    //workWithStatelessKieSession(kieSession);
	    
//	    try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    InternalKnowledgeBase kbase = ctx.getBean(InternalKnowledgeBase.class);
//	    updateRules(kbase);
	    
	    //workWithStatelessKieSession(kieSession);
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
	
	public static void updateRules(InternalKnowledgeBase kbase){
		Map<String, InternalKnowledgePackage> packages = kbase.getPackagesMap();
		System.out.println("packages.keySet():"+packages.keySet());
		Set<String> packageNameSet = new HashSet<String>();
		packageNameSet.addAll(packages.keySet());
		for(String packageName:packageNameSet){
			kbase.removeKiePackage(packageName);
		}
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/grading/test_loop.drl"),ResourceType.DRL);
		Collection<KiePackage> newPkgs = kbuilder.getKnowledgePackages();		
		kbase.addPackages(newPkgs);
	}

}
