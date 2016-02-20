package com.betalife.santhiya.util

import org.apache.commons.collections.CollectionUtils

class UpdateClassify {
	List insertList
	List deleteList
	List updateList

	UpdateClassify(inputList, fromDBList, idStrList){

		def buildId = { item ->
			def ids = idStrList.collect { item[it] }
			ids.join("-")
		}

		def (inputIds, inputMap) = build(inputList, buildId)
		def (fromDBIds, fromDBMap) = build(fromDBList, buildId)
		def updateIds = CollectionUtils.intersection(inputIds, fromDBIds)
		def deleteIds = CollectionUtils.subtract(fromDBIds, inputIds)
		def insertIds = CollectionUtils.subtract(inputIds, fromDBIds)

		updateList = updateIds.collect { inputMap[it] }
		deleteList = deleteIds.collect { fromDBMap[it] }
		insertList = insertIds.collect { inputMap[it] }
		println "fromDBList==="+fromDBList
		println "updateIds==="+updateIds
		println "deleteIds==="+deleteIds
		println "insertIds==="+insertIds
	}

	def build(list, buildId){
		def ids = []
		def map = [:]
		list.each{
			def newId = buildId(it)
			ids << newId
			map[newId] = it
		}
		[ids, map]
	}
}

