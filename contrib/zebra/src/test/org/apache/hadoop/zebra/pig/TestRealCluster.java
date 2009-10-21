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

package org.apache.hadoop.zebra.pig;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.pig.backend.hadoop.datastorage.ConfigurationUtil;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.zebra.io.BasicTable;
import org.apache.hadoop.zebra.io.TableInserter;
import org.apache.hadoop.zebra.schema.Schema;
import org.apache.hadoop.zebra.types.TypesUtils;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Note:
 * 
 * Make sure you add the build/pig-0.1.0-dev-core.jar to the Classpath of the
 * app/debug configuration, when run this from inside the Eclipse.
 * 
 */
public class TestRealCluster {

  public static void main(String[] args) throws IOException, ExecException,
      Exception {

    Configuration conf = new Configuration();
    PigServer pigServer = new PigServer(ExecType.MAPREDUCE, ConfigurationUtil
        .toProperties(conf));
    String pp = "/user/harmeek/outputdata1/t1";
    pigServer
        .registerJar("/homes/harmeek/july14_investigation/pig-zebra-jul14.jar");
    String query = "records = LOAD '" + pp.toString()
        + "' USING org.apache.hadoop.zebra.pig.TableLoader();";
    pigServer.registerQuery(query);

    Iterator<Tuple> it = pigServer.openIterator("records");
    while (it.hasNext()) {
      Tuple cur = it.next();
      System.out.println(cur);
    }

    pigServer.shutdown();
    /*
     * Use pig STORE to store testing data
     */
    /*
     * pigServer.store("records", new Path(pathTable, "store").toString(),
     * TableStorer.class.getCanonicalName() +
     * "('[SF_a, SF_b, SF_c]; [SF_e]')" );
     */
  }
}
