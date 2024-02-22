/**
* originUri => Original file`s uri
* fileName => Original file`s fileName
*/
val resultUri
if(fileName != null  &&
    (fileName.indexOf(".heic") > -1 ||
     fileName.indexOf(".heif") > -1 ||
     fileName.indexOf(".heix") > -1)
    ) {

    // 고효율모드 heif 파일의 경우 jpeg로 파일을 변경한다.
    Timber.d("selected heif file... start convert...")

    val tempImageUri = ConvertImage.convertResizeImage(applicationContext, originUri)
    if(tempImageUri == originUri) {
        // 실패시
        throw Exception("heif convert fail")
    }else{
        // 성공시
        Timber.d("heif convert success")
        fileName = fileName.slice(0..fileName.indexOf(".")) + "jpeg"
        resultUri = tempImageUri
    }
} else
    resultUri = originUri
