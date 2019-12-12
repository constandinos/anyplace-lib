/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.anyplace_ros_client;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.service.ServiceServer;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Arrays;

import cy.ac.ucy.anyplace.AnyplacePost;
import anyplace_ros_custom_msgs.EstimatePosition;
import anyplace_ros_custom_msgs.EstimatePositionRequest;
import anyplace_ros_custom_msgs.EstimatePositionResponse;


/**
 * A simple {@link ServiceServer} {@link NodeMain}.
   The original code is created by:
 *
 * @author damonkohler@google.com (Damon Kohler)
 * The custom implementation is created by
   mickaram@hotmail.com (Mike Karamousadakis)
 */
public class Server extends AbstractNodeMain {

  private String root_namespace = "/anyplace_ros/";

  private AnyplacePost client = new AnyplacePost("ap-dev.cs.ucy.ac.cy", "443", "res/");

  @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("anyplace_ros_client/server");
  }


  @Override
  public void onStart(final ConnectedNode connectedNode) {

    /***************************************************
     * EstimatePosition Service
     * Define EstimatePositionService and it's callback
    ****************************************************/
    ServiceResponseBuilder<EstimatePositionRequest, EstimatePositionResponse> EstimatePositionService = new ServiceResponseBuilder<EstimatePositionRequest, EstimatePositionResponse>() {
      @Override
      public void build(EstimatePositionRequest request, EstimatePositionResponse response) {
          //Create an array with the size of request.getSize()
          // long[] tmpArray=new long[request.getSize()];
          // for(int i=0; i<request.getSize();i++){
          //     tmpArray[i]=i;
          // }
          //  response.setRes(tmpArray);
          //      connectedNode.getLog().info(
          //              String.format("The size of the array will be "+request.getSize()));
        
        String operating_system = request.getOperatingSystem();
				String buid = request.getBuid();
				String floor = request.getFloor();
        String algorithm = request.getAlgorithm();
        
        if(operating_system.isEmpty() || buid.isEmpty() || floor.isEmpty() || algorithm.isEmpty()){
          response.setSuccess(false);
          response.setResponse("Service parameters cannot be empty!\n Returning...");
          return;
        }
				
				String cmd[] = new String[3];
				if (operating_system.equals("linux")) {
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
					cmd[2] = "sudo iwlist wlo1 scan | awk  '/Address/ {print $5}; /level/ {print $3}' |  cut -d\"=\" -f2 ";
				}
				else if (operating_system.equals("mac")) {
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
					cmd[2] = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport -s | grep ':' | tr -s ' ' | cut -d' ' -f3 -f4| tr ' ' '\n'";
					
				}
				else {
          response.setSuccess(false);
          response.setResponse("Only linux and mac are the available operating systems\n Returning...");
          return;
				}

				String aps[] = new String[200];
				Process p;
				String s, temp;
				int counter = 0;
				try {
					p = Runtime.getRuntime().exec(cmd);

					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((s = br.readLine()) != null && counter <= 20) {
						temp = "{\"bssid\":\"";
						temp += s;
						temp += "\",\"rss\":";
						s = br.readLine();
						temp += s;
						temp += "}";
						temp = temp.toLowerCase();
						aps[counter++] = temp;
					}
					p.destroy();
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				aps=Arrays.copyOf(aps, counter);
				for (int j=0; j<counter;j++) {
					connectedNode.getLog().info(aps[j]);
				}
				String anyplace_response = client.estimatePosition(buid, floor, aps, algorithm);
        connectedNode.getLog().info(anyplace_response + "\n"); /* .substring(0, 100) */

        response.setSuccess(true);
        response.setResponse(anyplace_response);
      }
    };
    connectedNode.newServiceServer(root_namespace + "estimate_position", EstimatePosition._TYPE, EstimatePositionService);



    /***************************************************
     * EstimatePositionOffline Service
     * Define EstimatePositionOfflineService and it's callback
    ****************************************************/
    ServiceResponseBuilder<EstimatePositionRequest, EstimatePositionResponse> EstimatePositionOfflineService = new ServiceResponseBuilder<EstimatePositionRequest, EstimatePositionResponse>() {
      @Override
      public void build(EstimatePositionRequest request, EstimatePositionResponse response) {
          //Create an array with the size of request.getSize()
          // long[] tmpArray=new long[request.getSize()];
          // for(int i=0; i<request.getSize();i++){
          //     tmpArray[i]=i;
          // }
          //  response.setRes(tmpArray);
          //      connectedNode.getLog().info(
          //              String.format("The size of the array will be "+request.getSize()));
        
        String operating_system = request.getOperatingSystem();
				String buid = request.getBuid();
				String floor = request.getFloor();
        String algorithm = request.getAlgorithm();
        
        if(operating_system.isEmpty() || buid.isEmpty() || floor.isEmpty() || algorithm.isEmpty()){
          response.setSuccess(false);
          response.setResponse("Service parameters cannot be empty!\n Returning...");
          return;
        }
				
				String cmd[] = new String[3];
				if (operating_system.equals("linux")) {
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
					cmd[2] = "sudo iwlist wlo1 scan | awk  '/Address/ {print $5}; /level/ {print $3}' |  cut -d\"=\" -f2 ";
				}
				else if (operating_system.equals("mac")) {
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
					cmd[2] = "/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport -s | grep ':' | tr -s ' ' | cut -d' ' -f3 -f4| tr ' ' '\n'";
					
				}
				else {
          response.setSuccess(false);
          response.setResponse("Only linux and mac are the available operating systems\n Returning...");
          return;
				}

				String aps[] = new String[200];
				Process p;
				String s, temp;
				int counter = 0;
				try {
					p = Runtime.getRuntime().exec(cmd);

					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((s = br.readLine()) != null && counter <= 20) {
						temp = "{\"bssid\":\"";
						temp += s;
						temp += "\",\"rss\":";
						s = br.readLine();
						temp += s;
						temp += "}";
						temp = temp.toLowerCase();
						aps[counter++] = temp;
					}
					p.destroy();
					br.close();
        } 
        catch (Exception ex) {
					ex.printStackTrace();
				}
				aps=Arrays.copyOf(aps, counter);

				String anyplace_response = client.estimatePositionOffline(buid, floor, aps, algorithm);
        connectedNode.getLog().info(anyplace_response + "\n"); /* .substring(0, 100) */
        response.setSuccess(true);
        response.setResponse(anyplace_response);
			}
    };
    connectedNode.newServiceServer(root_namespace + "estimate_position_offline", EstimatePosition._TYPE, EstimatePositionOfflineService);    






  }

}