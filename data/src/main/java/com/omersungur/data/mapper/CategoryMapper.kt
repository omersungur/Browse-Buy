package com.omersungur.data.mapper

import com.omersungur.data.remote.dto.category.CategoryDto
import com.omersungur.domain.model.category.Category

fun CategoryDto.toCategoryItem(): Category {
    return Category(
        name = name,
        slug = slug,
        url = url,
    )
}