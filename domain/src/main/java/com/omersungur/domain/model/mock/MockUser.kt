package com.omersungur.domain.model.mock

import com.omersungur.domain.model.user.Address
import com.omersungur.domain.model.user.Bank
import com.omersungur.domain.model.user.Company
import com.omersungur.domain.model.user.Coordinates
import com.omersungur.domain.model.user.Crypto
import com.omersungur.domain.model.user.Hair
import com.omersungur.domain.model.user.User

val mockUser = User(
    address = Address(
        address = "Example Address",
        city = "Phoenix",
        state = "Mississippi",
        stateCode = "MS",
        postalCode = "29112",
        coordinates = Coordinates(
            lat = -77.16213,
            lng = -92.084824
        ),
        country = "United States"
    ),
    age = 28,
    bank = Bank(
        cardExpire = "03/26",
        cardNumber = "9289760655481815",
        cardType = "Elo",
        currency = "CNY",
        iban = "YPUXISOBI7TTHPK2BR3HAIXL"
    ),
    birthDate = "1996-05-30",
    bloodGroup = "O-",
    company = Company(
        department = "Engineering",
        name = "Dooley, Kozey and Cronin",
        title = "Sales Manager",
        address = Address(
            address = "263 Tenth Street",
            city = "San Francisco",
            state = "Wisconsin",
            stateCode = "WI",
            postalCode = "37657",
            coordinates = Coordinates(
                lat = 71.814525,
                lng = -161.150263
            ),
            country = "United States"
        )
    ),
    crypto = Crypto(
        coin = "Bitcoin",
        wallet = "0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a",
        network = "Ethereum (ERC20)"
    ),
    ein = "977-175",
    email = "emily.johnson@x.dummyjson.com",
    eyeColor = "Green",
    firstName = "Emily",
    gender = "female",
    hair = Hair(
        color = "Brown",
        type = "Curly"
    ),
    height = 193.24,
    id = 1,
    image = "https://dummyjson.com/icon/emilys/128",
    ip = "42.48.100.32",
    lastName = "Johnson",
    macAddress = "47:fa:41:18:ec:eb",
    maidenName = "Smith",
    password = "emilyspass",
    phone = "+81 965-431-3024",
    role = "admin",
    ssn = "900-590-289",
    university = "University of Wisconsin--Madison",
    userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36",
    username = "johnson",
    weight = 63.16
)