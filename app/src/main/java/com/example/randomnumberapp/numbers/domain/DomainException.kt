package com.example.randomnumberapp.numbers.domain

abstract class DomainException: IllegalStateException()

class NoInternetConnectionException : DomainException()

class ServiceUnavailableException : DomainException()