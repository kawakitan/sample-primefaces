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
package com.github.kawakitan.sample.primefaces.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.github.kawakitan.sample.primefaces.entity.Employee;

/**
 *
 * @author kawakitan
 */
@Stateless
public class EmployeeService {

	@PersistenceContext(unitName = "pu")
	private EntityManager em;

	public Employee delete(final long pDeleteEmployeeId) {
		final Employee emp = this.em.find(Employee.class, Long.valueOf(pDeleteEmployeeId));
		this.em.remove(emp);
		return emp;
	}

	public List<Employee> getAll() {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		query.from(Employee.class);
		return this.em.createQuery(query).getResultList();
	}

	public Employee insert(final String pName) {
		final Employee newEmp = new Employee();
		newEmp.setName(pName);
		this.em.persist(newEmp);
		return newEmp;
	}
}