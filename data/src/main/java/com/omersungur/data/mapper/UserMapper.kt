package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.user.AddressDto
import com.omersungur.data.remote.dto.user.BankDto
import com.omersungur.data.remote.dto.user.CompanyDto
import com.omersungur.data.remote.dto.user.CoordinatesDto
import com.omersungur.data.remote.dto.user.CryptoDto
import com.omersungur.data.remote.dto.user.HairDto
import com.omersungur.data.remote.dto.user.UserDto
import com.omersungur.domain.model.user.Address
import com.omersungur.domain.model.user.Bank
import com.omersungur.domain.model.user.Company
import com.omersungur.domain.model.user.Coordinates
import com.omersungur.domain.model.user.Crypto
import com.omersungur.domain.model.user.Hair
import com.omersungur.domain.model.user.User

fun UserDto.toUser(): User {
    return User(
        address = address?.toAddress(),
        age = age,
        bank = bank?.toBank(),
        birthDate = birthDate,
        bloodGroup = bloodGroup,
        company = company?.toCompany(),
        crypto = crypto?.toCrypto(),
        ein = ein,
        email = email,
        eyeColor = eyeColor,
        firstName = firstName,
        gender = gender,
        hair = hair?.toHair(),
        height = height,
        id = id,
        image = image,
        ip = ip,
        lastName = lastName,
        macAddress = macAddress,
        maidenName = maidenName,
        password = password,
        phone = phone,
        ssn = ssn,
        university = university,
        userAgent = userAgent,
        username = username,
        weight = weight,
        role = role,
    )
}

fun AddressDto.toAddress(): Address {
    return Address(
        country = country,
        address = address,
        city = city,
        coordinates = coordinates?.toCoordinates(),
        postalCode = postalCode,
        state = state,
        stateCode = stateCode,
    )
}

fun CoordinatesDto.toCoordinates(): Coordinates {
    return Coordinates(
        lat = lat,
        lng = lng,
    )
}

fun BankDto.toBank(): Bank {
    return Bank(
        cardExpire = cardExpire,
        cardNumber = cardNumber,
        cardType = cardType,
        currency = currency,
        iban = iban,
    )
}

fun CompanyDto.toCompany(): Company {
    return Company(
        address = address?.toAddress(),
        department = department,
        name = name,
        title = title,
    )
}

fun CryptoDto.toCrypto(): Crypto {
    return Crypto(
        network = network,
        wallet = wallet,
        coin = coin,
    )
}

fun HairDto.toHair(): Hair {
    return Hair(
        color = color,
        type = type,
    )
}