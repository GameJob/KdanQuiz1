package bck.kdan.quiz1;

import java.io.File;
import java.io.IOException;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

public class FileUtils
{
	private static volatile FileUtils instance = null;

	private FileUtils()
	{
	}

	public static FileUtils getInstance()
	{
		if (instance == null)
		{
			synchronized (FileUtils.class)
			{
				if (instance == null)
				{
					instance = new FileUtils();
				}
			}
		}
		return instance;
	}

	public Bitmap getBitmapFromIntent(Context context, Intent data, float maxBoundSize)
	{
		Bitmap bitmap = null;
		Uri uri = data.getData();
		if (uri != null)
		{
			String path = getRealPathFromURI(context, uri);
			bitmap = BitmapFactory.decodeFile(path);

			// Samsung Galaxy Note 2 and S III doesn't return the image in the correct orientation, therefore rotate it based on the data held in the exif.
			int imageExifOrientation = 0;
			try
			{
				ExifInterface exif = new ExifInterface(path);
				imageExifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			}
			catch (IOException e)
			{
				Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
			}

			int rotationAmount = 0;
			if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
			{
				rotationAmount = 270;
			}
			else if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
			{
				rotationAmount = 90;
			}
			else if (imageExifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
			{
				rotationAmount = 180;
			}

            Matrix matrix = new Matrix();
            if (bitmap.getWidth() > maxBoundSize || bitmap.getHeight() > maxBoundSize)
            {
                float resize = maxBoundSize / Math.max(bitmap.getWidth(), bitmap.getHeight());                
                matrix.postScale(resize, resize);
            }
			if (rotationAmount != 0)
			{
				matrix.postRotate(rotationAmount);
			}
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
		return bitmap;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public String getRealPathFromURI(Context context, Uri uri)
	{
		final boolean isKitKat = Build.VERSION.SDK_INT >= 19;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri))
		{
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri))
			{
				final String docId = DocumentsContract.getDocumentId(uri);
				final String [] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type))
				{
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
				else
				{
					return new File(uri.toString()).getAbsolutePath();
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri))
			{
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri))
			{
				final String docId = DocumentsContract.getDocumentId(uri);
				final String [] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type))
				{
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				}
				else if ("video".equals(type))
				{
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				}
				else if ("audio".equals(type))
				{
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String [] selectionArgs = new String [] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme()))
		{
			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme()))
		{
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String [] selectionArgs)
	{
		Cursor cursor = null;
		final String column = "_data";
		final String [] projection = { column };

		try
		{
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst())
			{
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		}
		finally
		{
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri)
	{
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri)
	{
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri)
	{
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri)
	{
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
