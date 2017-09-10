/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kawakitan.sample.primefaces.view;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.github.kawakitan.sample.primefaces.entity.Employee;
import com.github.kawakitan.sample.primefaces.service.EmployeeService;

/**
 *
 * @author kawakitan
 */
@ManagedBean(name = "employeeView")
@RequestScoped
public class EmployeeView {

	@Inject
	private EmployeeService employeeService;

	private String name;

	private List<Employee> employees;

	public String regist() {
		this.employeeService.insert(name);

		this.employees = this.employeeService.getAll();

		return "employee.xhtml";
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
}
