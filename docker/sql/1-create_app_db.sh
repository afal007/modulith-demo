#!/bin/bash

psql -U dev -tc "SELECT 1 FROM pg_database WHERE datname = 'modulith'" |
  grep -q 1 || psql -U dev -c "CREATE DATABASE modulith"
