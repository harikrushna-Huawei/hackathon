/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.flowapi;

import org.onlab.packet.IpAddress;

/**
 * Extended flow traffic action class.
 */
public interface ExtTrafficActionRedirectToIp extends ExtFlowTypes {

    /**
     * Returns the ExtType.
     *
     * @return the ExtType
     */
    ExtType type();

    /**
     * Returns the IP address action.
     *
     * @return the IP address
     */
    IpAddress ipAddress();

    /**
     * Returns the copy.
     *
     * @return the copy
     */
    Short copy();

    /**
     * Returns whether this traffic action is an exact match to the traffic action given
     * in the argument.
     *
     * @param trafficAction other traffic action to match against
     * @return true if the traffic action are an exact match, otherwise false
     */
    boolean exactMatch(ExtTrafficActionRedirectToIp trafficAction);

    /**
     * A traffic action builder..
     */
    interface Builder {

        /**
         * Assigns the ExtType to this object.
         *
         * @param type extended type
         * @return this the builder object
         */
        Builder setType(ExtType type);

        /**
         * Assigns the terminal action to this object.
         *
         * @param ipAddress action
         * @return this the builder object
         */
        Builder setIpAddress(IpAddress ipAddress);

        /**
         * Assigns the traffic sampling to this object.
         *
         * @param copy to be done or not
         * @return this the builder object
         */
        Builder setCopy(Short copy);

        /**
         * Builds a traffic action object.
         *
         * @return a traffic action object.
         */
        ExtTrafficActionRedirectToIp build();
    }
}
