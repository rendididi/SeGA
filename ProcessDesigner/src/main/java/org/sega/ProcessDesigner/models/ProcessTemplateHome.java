package org.sega.ProcessDesigner.models;

// Generated 2015-8-13 14:50:04 by Hibernate Tools 4.3.1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.service.ServiceRegistry;

/**
 * Home object for domain model class ProcessTemplate.
 * @see org.sega.ProcessDesigner.models.ProcessTemplate
 * @author Hibernate Tools
 */
public class ProcessTemplateHome {

	private static final Log log = LogFactory.getLog(ProcessTemplateHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry  serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
		            configuration.getProperties()).build();
			return configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(ProcessTemplate transientInstance) {
		log.debug("persisting ProcessTemplate instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(ProcessTemplate instance) {
		log.debug("attaching dirty ProcessTemplate instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProcessTemplate instance) {
		log.debug("attaching clean ProcessTemplate instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(ProcessTemplate persistentInstance) {
		log.debug("deleting ProcessTemplate instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProcessTemplate merge(ProcessTemplate detachedInstance) {
		log.debug("merging ProcessTemplate instance");
		try {
			ProcessTemplate result = (ProcessTemplate) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public List<ProcessTemplate> findAll() {
		log.debug("getting ProcessTemplate instances: ");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			List<ProcessTemplate> instance = (List<ProcessTemplate>) session.createCriteria(ProcessTemplate.class).list();
			session.getTransaction().commit();
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public ProcessTemplate findById(long id) {
		log.debug("getting ProcessTemplate instance with id: " + id);
		try {
			ProcessTemplate instance = (ProcessTemplate) sessionFactory
					.getCurrentSession().get(
							"org.sega.ProcessDesigner.models.ProcessTemplate",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ProcessTemplate instance) {
		log.debug("finding ProcessTemplate instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"org.sega.ProcessDesigner.models.ProcessTemplate")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
