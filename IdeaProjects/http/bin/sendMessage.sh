# -------------------------------------------------------------------------
#   (C) Copyright 2007-2008 hSenid International (pvt) Limited.
#   All Rights Reserved.
#
#   These materials are unpublished, proprietary, confidential source code of
#   hSenid International (pvt) Limited and constitute a TRADE SECRET of hSenid
#   International (pvt) Limited.
#
#   hSenid International (pvt) Limited retains all title to and intellectual
#   property rights in these materials.
#
# ------------------------------------------------------------------------
#
#   Message sending script for HTTP Response for DST-MMP
#
# -------------------------------------------------------------------------

#
# The simulation responder host.
#
HTTP_SIMULATION_HOST=localhost

#
# The simulation responder listen port.
#
HTTP_SIMULATION_PORT=8081

#
# The simulation responder URI.
#
HTTP_SIMULATION_URI="/mdp-http-responder/httpresponder?account=httpcon&password=password&queue=inbox&timeout=10000"

java -cp ../lib/agora-3.1.jar:../lib/mdp-unittest-1.0.jar:../lib/log4j-1.2.12.jar:../lib/junit-3.8.1.jar:../lib/jdom-1.0.jar:../lib/:../lib/commons-httpclient-3.0.1.jar:../lib/commons-logging-1.1.jar:../lib/commons-codec-1.3.jar:../lib/httpsim-v1.jar:../conf hsenidmobile.mmp.httpsim.HttpMessageSender $HTTP_SIMULATION_HOST $HTTP_SIMULATION_PORT $HTTP_SIMULATION_URI $@
