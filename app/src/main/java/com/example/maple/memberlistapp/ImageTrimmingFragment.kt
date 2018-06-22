package com.example.maple.memberlistapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import com.isseiaoki.simplecropview.callback.LoadCallback
import com.isseiaoki.simplecropview.callback.SaveCallback
import kotlinx.android.synthetic.main.fragment_image_trimming.*

class ImageTrimmingFragment : Fragment() {

    companion object {
        val TAG: String = ImageTrimmingFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_image_trimming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        val bundle: Bundle = arguments!!
        val uri:    Uri    = bundle.getParcelable("uri")

        mTrimmingBtn.setOnClickListener {
            mCropImageView.startCrop(uri, mCropCallback, mSaveCallback)
        }

        mCropImageView.setCropMode(CropImageView.CropMode.CIRCLE) //円形でトリミングする
        mCropImageView.startLoad(uri, mLoadCallback)
    }

    private val mLoadCallback: LoadCallback = object :LoadCallback {
        override fun onSuccess() {
            Log.d(TAG, "mLoadCallback_onSuccess")
        }

        override fun onError(e: Throwable?) {
            throw e!!
        }
    }

    private val mCropCallback: CropCallback = object :CropCallback {
        override fun onSuccess(cropped: Bitmap?) {
            Log.d(TAG, "mCropCallback_onSuccess")
            arguments!!.putParcelable("cropimg", cropped)

            val CODE = (targetFragment as MemberEditFragment).getTrimmingTargetCode()
            val data: Intent = Intent()
            data.putExtra("tri", cropped)
            fragmentManager!!.popBackStack()
            targetFragment!!.onActivityResult(CODE, Activity.RESULT_OK, data)
        }

        override fun onError(e: Throwable?) {
            e!!.printStackTrace()
        }
    }

    private val mSaveCallback: SaveCallback = object :SaveCallback {
        override fun onSuccess(uri: Uri?) {
        }

        override fun onError(e: Throwable?) {
            e!!.stackTrace
        }
    }
}