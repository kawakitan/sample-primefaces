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
package com.github.kawakitan.sample.primefaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.h2.jdbcx.JdbcDataSource;

/**
 *
 * @author kawakitan
 */
public class Application {

	public static void main(final String[] pArgs) throws Exception {
		final long s = System.currentTimeMillis();

		final GlassFishProperties properties = new GlassFishProperties();
		properties.setPort("http-listener", 8081);
		properties.setPort("https-listener", 8082);

		// サーバ起動
		final GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish(properties);
		glassfish.start();

		final String connectionPoolName = "AppConnectionPool";
		glassfish.getCommandRunner().run("create-jdbc-connection-pool" //
				, "--datasourceclassname=" + JdbcDataSource.class.getName() //
				, "--restype=" + DataSource.class.getName() //
				, "--property", "url=jdbc\\:h2\\:./db/db" //
				, connectionPoolName //
				);

		glassfish.getCommandRunner().run("create-jdbc-resource" //
				, "--connectionpoolid", connectionPoolName //
				, "jdbc/App" //
		);

		// Webアプリを配備.
		glassfish.getDeployer().deploy( //
				new File("src/main/webapp") //
				, "--name=app" //
				, "--contextroot", "/");

		System.out.println("GlassFishサーバを起動しました.");
		System.out.println(System.currentTimeMillis() - s + " ms");

		// Enterを押せばサーバ停止.
		new BufferedReader(new InputStreamReader(System.in)).readLine();
		glassfish.stop();
		System.out.println("GlassFishサーバを停止.");
	}
}
