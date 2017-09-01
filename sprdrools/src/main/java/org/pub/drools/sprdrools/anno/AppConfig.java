/**
 * 
 */
package org.pub.drools.sprdrools.anno;

import java.util.Collection;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuhuoxin 2017年8月30日 下午2:52:28
 *
 */
@Configuration
public class AppConfig {

//	@Bean
//	public StatelessKieSession getStatelessKieSession(){
//		KieServices ks = KieServices.Factory.get();
//		KieContainer kc = ks.getKieClasspathContainer();
//		StatelessKieSession ksession = kc.newStatelessKieSession("gradingsession2");
//		return ksession;
//	}
	
	
//	@Bean(name = "gradingsession2")
//	public StatelessKieSession getStatelessKieSession2(){
//		KieServices kieServices = KieServices.Factory.get();
//		
//		KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
//
//		KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel( "KBase1 ")
//		        .setDefault( true )
//		        .setEqualsBehavior( EqualityBehaviorOption.EQUALITY )
//		        .setEventProcessingMode( EventProcessingOption.STREAM );
//
//		KieSessionModel ksessionModel1 = kieBaseModel1.newKieSessionModel( "KSession1" )
//		        .setDefault( true )
//		        .setType( KieSessionModel.KieSessionType.STATEFUL )
//		        .setClockType( ClockTypeOption.get("realtime") );
//		
//		
//
//		KieFileSystem kfs = kieServices.newKieFileSystem();
//		kfs.writeKModuleXML(kieModuleModel.toXML());
//		KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
//		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
//		
//		KnowledgeBuilder kbuilder=KnowledgeBuilderFactory.newKnowledgeBuilder();
//		kbuilder.add(ResourceFactory.newClassPathResource("rules.grading.test_loop"),ResourceType.DRL);
//		
//		return kieContainer.newStatelessKieSession();
//	}
	
	@Bean
	public InternalKnowledgeBase getInternalKnowledgeBase(){
		System.out.println("get kie base");
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/grading/test_loop.drl"),ResourceType.DRL);
		Collection<KiePackage> kpackage = kbuilder.getKnowledgePackages();

		KieBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		//kbConf.setProperty("org.drools.sequential", "true");
		InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);	
		kbase.addPackages(kpackage);
		return kbase;
	}
	
	@Bean(name = "gradingsession3")
	public StatelessKieSession getStatelessKieSession3(){
		System.out.println("get kie session");
		InternalKnowledgeBase kbase = getInternalKnowledgeBase();
		kbase = getInternalKnowledgeBase();
		
		StatelessKieSession statefulKSession=kbase.newStatelessKieSession();
		return statefulKSession;
	}
	

}
