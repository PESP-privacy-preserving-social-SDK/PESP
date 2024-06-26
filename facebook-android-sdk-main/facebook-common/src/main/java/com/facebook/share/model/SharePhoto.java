/*
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.facebook.share.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.facebook.internal.qualityvalidation.Excuse;
import com.facebook.internal.qualityvalidation.ExcusesForDesignViolations;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a photo for sharing.
 *
 * <p>Use {@link SharePhoto.Builder} to build instances
 */
@ExcusesForDesignViolations(@Excuse(type = "MISSING_UNIT_TEST", reason = "Legacy"))
public final class SharePhoto extends ShareMedia {
  private final Bitmap bitmap;
  private final Uri imageUrl;
  private final boolean userGenerated;
  private final String caption;

  private SharePhoto(final Builder builder) {
    super(builder);
    this.bitmap = builder.bitmap;
    this.imageUrl = builder.imageUrl;
    this.userGenerated = builder.userGenerated;
    this.caption = builder.caption;
  }

  SharePhoto(final Parcel in) {
    super(in);
    this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    this.imageUrl = in.readParcelable(Uri.class.getClassLoader());
    this.userGenerated = (in.readByte() != 0);
    this.caption = in.readString();
  }

  /**
   * If the photo is resident in memory, this method supplies the data.
   *
   * @return {@link android.graphics.Bitmap} representation of the photo.
   */
  @Nullable
  public Bitmap getBitmap() {
    return this.bitmap;
  }

  /**
   * The URL to the photo.
   *
   * @return {@link android.net.Uri} that points to a network location or the location of the photo
   *     on disk.
   */
  @Nullable
  public Uri getImageUrl() {
    return this.imageUrl;
  }

  /**
   * Specifies whether the photo represented by this object was generated by the user or by the
   * application.
   *
   * @return Indication of whether the photo is user-generated.
   */
  public boolean getUserGenerated() {
    return this.userGenerated;
  }

  /**
   * Gets the user generated caption. Note that the 'caption' must come from the user, as pre-filled
   * content is forbidden by the Platform Policies (2.3).
   *
   * @return The user generated caption.
   */
  public String getCaption() {
    return this.caption;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(final Parcel out, final int flags) {
    super.writeToParcel(out, flags);
    out.writeParcelable(this.bitmap, 0);
    out.writeParcelable(this.imageUrl, 0);
    out.writeByte((byte) (this.userGenerated ? 1 : 0));
    out.writeString(caption);
  }

  public static final Parcelable.Creator<SharePhoto> CREATOR =
      new Parcelable.Creator<SharePhoto>() {

        @Override
        public SharePhoto createFromParcel(final Parcel source) {
          return new SharePhoto(source);
        }

        @Override
        public SharePhoto[] newArray(final int size) {
          return new SharePhoto[size];
        }
      };

  @Override
  public Type getMediaType() {
    return Type.PHOTO;
  }

  /** Builder for the {@link com.facebook.share.model.SharePhoto} class. */
  public static final class Builder extends ShareMedia.Builder<SharePhoto, Builder> {
    private Bitmap bitmap;
    private Uri imageUrl;
    private boolean userGenerated;
    private String caption;

    /**
     * Sets the bitmap representation of the photo.
     *
     * @param bitmap {@link android.graphics.Bitmap} representation of the photo.
     * @return The builder.
     */
    public Builder setBitmap(@Nullable final Bitmap bitmap) {
      this.bitmap = bitmap;
      return this;
    }

    /**
     * Sets the URL to the photo.
     *
     * @param imageUrl {@link android.net.Uri} that points to a network location or the location of
     *     the photo on disk.
     * @return The builder.
     */
    public Builder setImageUrl(@Nullable final Uri imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    /**
     * Sets whether the photo represented by this object was generated by the user or by the
     * application.
     *
     * @param userGenerated Indication of whether the photo is user-generated.
     * @return The builder.
     */
    public Builder setUserGenerated(final boolean userGenerated) {
      this.userGenerated = userGenerated;
      return this;
    }

    /**
     * Sets the user generated caption for the photo. Note that the 'caption' must come from the
     * user, as pre-filled content is forbidden by the Platform Policies (2.3).
     *
     * @param caption {@link java.lang.String} of a {@link com.facebook.share.model.SharePhoto}
     * @return The builder.
     */
    public Builder setCaption(@Nullable final String caption) {
      this.caption = caption;
      return this;
    }

    // Accessor that is present only for ShareVideoContent(Parcel) to use
    Uri getImageUrl() {
      return imageUrl;
    }

    // Accessor that is present only for ShareVideoContent(Parcel) to use
    Bitmap getBitmap() {
      return bitmap;
    }

    @Override
    public SharePhoto build() {
      return new SharePhoto(this);
    }

    @Override
    public Builder readFrom(final SharePhoto model) {
      if (model == null) {
        return this;
      }
      return super.readFrom(model)
          .setBitmap(model.getBitmap())
          .setImageUrl(model.getImageUrl())
          .setUserGenerated(model.getUserGenerated())
          .setCaption(model.getCaption());
    }

    Builder readFrom(final Parcel parcel) {
      return this.readFrom((SharePhoto) parcel.readParcelable(SharePhoto.class.getClassLoader()));
    }

    /*
     * For backwards compatibility with SharePhotoContent.  Prefer ShareMediaContent for new
     * code.
     */
    static void writePhotoListTo(final Parcel out, int parcelFlags, final List<SharePhoto> photos) {
      ShareMedia[] array = new ShareMedia[photos.size()];
      for (int i = 0; i < photos.size(); ++i) {
        array[i] = photos.get(i);
      }
      out.writeParcelableArray(array, parcelFlags);
    }

    /*
     * For backwards compatibility with SharePhotoContent.  Prefer ShareMediaContent for new
     * code.
     */
    static List<SharePhoto> readPhotoListFrom(final Parcel in) {
      final List<ShareMedia> media = readListFrom(in);
      final List<SharePhoto> photos = new ArrayList<>();
      for (ShareMedia medium : media) {
        if (medium instanceof SharePhoto) {
          photos.add((SharePhoto) medium);
        }
      }
      return photos;
    }
  }
}
