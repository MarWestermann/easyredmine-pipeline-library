#!/usr/bin/groovy

/**
 *
 * @param data
 * -filename: file to convert to base64
 */
def call(Map data) {
    String filename = data.filename
    return new File(filename).readBytes().encodeBase64().toString()
}

return this
