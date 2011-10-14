ANDROID-IMS
===========

Git fork of dead project from http://code.google.com/p/android-ims

From original project site:

This project has as objective to develop an IMS stack for android platform in order to provide a common framework for building IMS services.

IMS stands for IP Multimedia Subsystem, a 3G and NGN framework or subsystem, which is already being used for several operators and services providers to bring IP multimedia services - such us VoIP, Presence, Push to Talk, Conference and many others.

This project will also include a sample application demonstrating the following:
* A VoIP client
* A Instant Messaging Client
* A Presence - Group/Buddy list status and management.

The IMS API for Android follows the following packaging structure:
* package com.android.ims
* package com.android.ims.core
* package com.android.ims.core.media
* package com.android.ims.cose
* package com.android.ims.cose.presence
* package com.android.ims.cose.messaging
* package com.android.ims.cose.mmtel
* package com.android.ims.provisioning
* package com.android.ims.provisioning.xdm

OVERVIEW
--------

Introduction

Androims is implemented as a library which adds IMS capabilities into android applications, so developers do not need to write any low level SIP/RTP/XCAP code. Androims enables wide IMS support; hence, you can define the IMS service you want to use, define the right settings, invoke the right methods on the given interfaces and you will be able to communication with the IMS network and to be notified of events related to it.

Details

JAIN SIP

JAIN SIP - JSR32 - was the first step of the industry to improve the development of multimedia applications using the SIP protocol. JAIN SIP provides among others: SIP protocol abstraction, dialog handling, transaction support and encapsulates most of the SIP protocol implementation details.

IMS Core API

For accessing the basic IMS functionality (IMS Core Network) and provide a framework for building IMS communication services and it also provide a common way of accessing the real time media and the frame media. Based on JSR-281 philosophy, Androims represents an abstraction of the IMS protocols and its implementation is based JAIN SIP and Android Media Specifications.

IMS Communication Services API

According to TS 23.228, "an IMS communication service is a type of communication defined by a service definition that specifies the rules and procedures and allowed medias for a specific type of communication and that utilizes the IMS enablers". Examples of Communication Services are OMA Push To Talk over Cellular (PoC) or OMA SIMPLE Instant Messaging (also called IMS Messaging in 3GPP specifications), 3GPP MMTEL.

In other words a Communication Service is a set of communication media and the rules that govern the possible (i.e. permitted) transactions between them. For instance, OMA SIMPLE Instant Messaging only permits SIP sessions to include messaging components. Trying to upgrade a messaging session into a voice session is not part of the OMA SIMPLE IM service, and can be considered as a violation of the OMA SIMPLE IM Communication Service.

In the Androims, an IMS Communication Services (CoSe) represents a set of well defined IMS services (standardized or not). IMS CoSe will offer a clean interface to such services, in a way that applications may reuse them (reusable IMS components). For those standardized services, the CoSe services will try to follow the same approach as it is currently being developed by JSR325.

Andriod IMS Applications

It refers to those applications being developed by Android developers who are making use of the benefits given by the Androims API. An IMS Application contains business logic specific functionality: voice mail service for a specific company, collaboration apps integrating mobile users, advertising service, game developers, etc.

