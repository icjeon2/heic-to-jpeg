fun convertResizeImage(context : Context, uri : Uri): Uri {
        try {
            val contentResolver = context.contentResolver

            // 이미지 uri로 비트맵 변수를 생성
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }

            // 비트맵 이미지 resize 필요시 사용
//            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)

            // 비트맵을 byteArray로 변환
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

            // 캐시 디렉토리에 임시파일을 생성
            val tempFile = File.createTempFile("recreated_image", ".jpeg", context.cacheDir)
            val fileOutputStream = FileOutputStream(tempFile)
            try {
                // 생성한 byteArray를 임시파일에 write
                fileOutputStream.write(byteArrayOutputStream.toByteArray())
            } catch (e: Exception) {
                Timber.e(e)
                return uri
            } finally {
                fileOutputStream.close()
            }

            // uri 반환
            return Uri.fromFile(tempFile)
        }catch (e: Exception) {
            Timber.e(e)
            return uri
        }
    }
