package org.sega.ProcessDesigner.data;

import java.util.ArrayList;
import java.util.Arrays;

import com.mysql.fabric.xmlrpc.base.Array;

public final class StepConstant {

	public static ArrayList<String> step_names = new ArrayList<String>(Arrays.asList(
			"Select Process Template",
			"Customize Entity",
			"DB Template-EDB Mapping",
			"Entity-EDB Mapping",
			"Customize Process",
			"Service Binding",
			"Publish"
		));

	public static ArrayList<String> action_names = new ArrayList<String>(Arrays.asList(
		"step-process-select",
		"step-custom-entity",
		"step-dbtemplate-edb-mapping",
		"step-entity-edb-mapping",
		"step-custom-process",
		"step-bind-process",
		"step-publish"
		));
}
