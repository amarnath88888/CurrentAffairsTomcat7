package com.affairs.controller;

import junit.framework.TestCase;

import com.affairs.service.MetricsManager;

public class MetricsController extends TestCase{
	
	private MetricsManager metricsManager;
	
	protected void setUp() throws Exception {
		metricsManager = new MetricsManager();
	}
	/*public void testEntities() {
		BasicDBObject filter = new BasicDBObject("type", "tower");
		metricsManager.getEntityObjects(filter);
		filter = new BasicDBObject("type", "platform").append("tower", "WATCH-TAM");
		metricsManager.getEntityObjects(filter);
		filter = new BasicDBObject("type", "program").append("platform", "Ingestion").append("tower", "WATCH-TAM");
		metricsManager.getEntityObjects(filter);
	}*/
	/*public void testSDLC() {
		metricsManager.getSdlcObjects();
	}
	public void testSayDo() {
		metricsManager.getSayDoObjects();
	}*/
	public void testCurrent() {
		metricsManager.getCurrentObjects();
	}
}
