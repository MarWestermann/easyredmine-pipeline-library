#!/usr/bin/groovy

/**
 *
 * @param data
 * -filename: file to convert to base64
 */
def call(Map data) {
    String filename = data.filename
    echo pwd
    return Base64.getEncoder().encode(new File(filename).readBytes())
}

return this
