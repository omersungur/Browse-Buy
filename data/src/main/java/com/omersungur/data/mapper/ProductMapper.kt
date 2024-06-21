package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.product.DimensionsDto
import com.omersungur.data.remote.dto.product.MetaDto
import com.omersungur.data.remote.dto.product.ProductDto
import com.omersungur.data.remote.dto.product.ProductXDto
import com.omersungur.data.remote.dto.product.ReviewDto
import com.omersungur.domain.model.product.Dimensions
import com.omersungur.domain.model.product.Meta
import com.omersungur.domain.model.product.Product
import com.omersungur.domain.model.product.ProductX
import com.omersungur.domain.model.product.Review

fun ProductDto.toProduct(): Product {
    return Product(
        limit = limit,
        products = products?.map { it.toProductX() },
        skip = skip,
        total = total,
    )
}

fun ProductXDto.toProductX(): ProductX {
    return ProductX(
        availabilityStatus = availabilityStatus,
        brand = brand,
        category = category,
        description = description,
        dimensions = dimensions?.toDimensions(),
        discountPercentage = discountPercentage,
        id = id,
        images = images,
        meta = meta?.toMeta(),
        minimumOrderQuantity = minimumOrderQuantity,
        price = price,
        rating = rating,
        returnPolicy = returnPolicy,
        reviews = reviews?.map { it.toReview() },
        shippingInformation = shippingInformation,
        sku = sku,
        stock = stock,
        tags = tags,
        thumbnail = thumbnail,
        title = title,
        warrantyInformation = warrantyInformation,
        weight = weight,
    )
}

fun DimensionsDto.toDimensions(): Dimensions {
    return Dimensions(
        depth = depth,
        height = height,
        width = width,
    )
}

fun MetaDto.toMeta(): Meta {
    return Meta(
        barcode = barcode,
        createdAt = createdAt,
        qrCode = qrCode,
        updatedAt = updatedAt,
    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        comment = comment,
        date = date,
        rating = rating,
        reviewerName = reviewerEmail,
        reviewerEmail = reviewerEmail,
    )
}
