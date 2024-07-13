package com.ibrahim.utility.mappers

interface BaseMapper<FirstModel, SecondModel> {
  fun map(model: FirstModel): SecondModel
}