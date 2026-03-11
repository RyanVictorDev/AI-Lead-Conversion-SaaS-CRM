# PROJECT

AI Lead Conversion SaaS

A scalable SaaS platform that helps businesses capture, manage and convert leads from WhatsApp and other channels using automation, AI assistance and analytics.

The system must be production ready, secure, scalable and optimized for SaaS growth.

---

# PRODUCT VISION

Businesses lose leads every day because conversations are disorganized.

This platform centralizes:

• leads
• conversations
• sales pipeline
• follow-ups
• automation
• AI assistance

Main goal: increase lead conversion.

---

# TARGET MARKET

Small and medium businesses:

• clinics
• digital marketers
• course sellers
• freelancers
• real estate agents

---

# CORE FEATURES

Lead management
Sales pipeline
WhatsApp conversations
Follow-up automation
AI reply suggestions
Analytics dashboard
Multi-user team collaboration

---

# ADVANCED SAAS FEATURES

Feature Flags

Allow enabling/disabling features per plan.

Example:

AI replies only for PRO plan.

---

Event Driven Architecture

Events:

lead_created
message_received
task_created
lead_converted

Events trigger automations.

---

Job Queue

Use background jobs for:

sending emails
AI processing
analytics aggregation
notifications

---

Redis Cache

Used for:

session cache
API rate limiting
analytics cache
job queue

---

WebSockets

Real-time updates:

new messages
new leads
pipeline changes

---

# TECH STACK

Frontend

Quasar Framework
Vue 3
TypeScript
Pinia
Axios
Socket.IO client

---

Backend

Java 21
Spring Boot 3
Spring Security
JWT Authentication
Spring WebSocket
Spring Events

---

Infrastructure

PostgreSQL
Redis
Docker
Docker Compose
Nginx

---

Integrations

OpenAI API
WhatsApp API abstraction
Email SMTP
Payment gateway

---

# SYSTEM ARCHITECTURE

Layers

presentation
application
domain
infrastructure

Modules

auth
users
leads
pipeline
conversations
automation
analytics
billing
notifications

---

# DATABASE DESIGN

Company

id
name
email
plan
created_at

---

User

id
name
email
password
role
company_id

---

Lead

id
name
phone
email
status
company_id
created_at

---

Conversation

id
lead_id
company_id

---

Message

id
conversation_id
sender
content
created_at

---

Pipeline

id
company_id
name

---

Stage

id
pipeline_id
name
order

---

Task

id
lead_id
title
due_date
status

---

AutomationRule

id
company_id
trigger_event
condition
action

---

FeatureFlag

id
feature_name
plan_required

---

AnalyticsDaily

id
company_id
date
leads
conversions
messages

---

# SECURITY

Implement

JWT authentication
password hashing BCrypt
rate limiting via Redis
request validation
global exception handler
secure headers
CORS protection

---

# BACKEND PACKAGES

config
security
controller
service
repository
entity
dto
mapper
event
queue
websocket
automation
analytics

---

# AUTOMATION ENGINE

Rules example:

IF

lead.status = CONTACTED
AND no message for 48h

THEN

create followup task

---

# AI MODULE

Use OpenAI.

Functions:

suggest reply
summarize conversation
classify lead intent

---

# REAL TIME SYSTEM

WebSocket events

new_message
new_lead
lead_stage_changed
task_created

---

# JOB QUEUE

Queue system processes:

AI responses
email sending
analytics aggregation

---

# CACHE

Redis caching for:

analytics queries
active conversations
session data

---

# FRONTEND STRUCTURE

src

pages
components
stores
services
composables
layouts

---

# MAIN PAGES

Login
Register
Dashboard
Leads
Pipeline
Conversations
Tasks
Analytics
Settings
Billing

---

# DASHBOARD

Widgets

Total leads
Conversion rate
Messages today
Tasks due

Charts

Leads timeline
Conversion funnel

---

# PIPELINE PAGE

Kanban style board.

Drag leads between stages.

---

# CONVERSATIONS PAGE

Chat interface.

Real-time messages.

AI suggestion button.

---

# ANALYTICS PAGE

Metrics

lead conversion rate
messages per lead
average response time

---

# BILLING SYSTEM

Plans

Starter
Pro
Agency

Limits

users
leads
AI usage

---

# TESTING

Backend

JUnit
Mockito

Frontend

Vitest

---

# OBSERVABILITY

Health endpoint

/actuator/health

Structured logging

---

# DOCKER SERVICES

frontend
backend
postgres
redis
nginx

---

# CI/CD

Pipeline stages

install
build
test
docker build
deploy

---

# DEVELOPMENT ORDER

1 initialize backend
2 create database models
3 implement authentication
4 implement leads module
5 implement pipeline module
6 implement conversations module
7 implement automation engine
8 implement analytics module
9 implement websocket system
10 integrate AI module
11 build frontend layout
12 connect APIs
13 implement real-time updates
14 dockerize project
15 prepare production deployment

---

# FINAL GOAL

A complete SaaS platform ready for production deployment and commercialization.