/*
 * Copyright 2019 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.project;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.Frame;
import com.google.ar.core.Pose;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.Texture;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This is an example activity that uses the Sceneform UX package to make common Augmented Faces
 * tasks easier.
 */
public class AugmentedFacesActivity extends AppCompatActivity {
  private static final String TAG = AugmentedFacesActivity.class.getSimpleName();

  private static final double MIN_OPENGL_VERSION = 3.0;
  private Pose centerPose;
  private com.project.FaceArFragment arFragment;
  private ModelRenderable faceRegionsRenderable;
  private Texture faceMeshTexture;

  private Anchor centerAnchor;
  private AnchorNode centerAnchorNode;
  private TextView tv;

  private float distanceBetweenLips;
  private float distanceBetweenREye;


  private final HashMap<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();

  @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }

    setContentView(R.layout.activity_face_mesh);
    tv = findViewById(R.id.tv);

    arFragment = (com.project.FaceArFragment) getSupportFragmentManager().findFragmentById(R.id.face_fragment);

    // Load the face regions renderable.
    // This is a skinned model that renders 3D objects mapped to the regions of the augmented face.
    ModelRenderable.builder()
        .setSource(this, R.raw.fox_face)
        .build()
        .thenAccept(
            modelRenderable -> {
              faceRegionsRenderable = modelRenderable;
              modelRenderable.setShadowCaster(false);
              modelRenderable.setShadowReceiver(false);
            });

    // Load the face mesh texture.
    Texture.builder()
        .setSource(this, R.drawable.fox_face_mesh_texture)
        .build()
        .thenAccept(texture -> faceMeshTexture = texture);

    ArSceneView sceneView = arFragment.getArSceneView();

    // This is important to make sure that the camera stream renders first so that
    // the face mesh occlusion works correctly.
    sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);

    Scene scene = sceneView.getScene();

    scene.addOnUpdateListener(
        (FrameTime frameTime) -> {
          if (faceRegionsRenderable == null || faceMeshTexture == null) {
            return;
          }
          Collection<AugmentedFace> faceList =
              sceneView.getSession().getAllTrackables(AugmentedFace.class);

          // Make new AugmentedFaceNodes for any new faces.
          for (AugmentedFace face : faceList) {
            if (!faceNodeMap.containsKey(face)) {
              AugmentedFaceNode faceNode = new AugmentedFaceNode(face);
              faceNode.setParent(scene);
              //faceNode.setFaceRegionsRenderable(faceRegionsRenderable);
              //faceNode.setFaceMeshTexture(faceMeshTexture);
              faceNodeMap.put(face, faceNode);
            }

            FloatBuffer buffer = face.getMeshVertices();
            Vector3 vectorDownLips = new Vector3(buffer.get(17*3),buffer.get((17*3)+1),buffer.get((17*3)+2));
            Vector3 vectorUpLips = new Vector3(buffer.get((13*3)),buffer.get((13*3)+1),buffer.get((13*3)+2));

            Vector3 vectorDownRightEye = new Vector3(buffer.get(23*3),buffer.get(23*3+1),buffer.get(23*3+2));
            Vector3 vectorUpRightEye = new Vector3(buffer.get(27*3),buffer.get(27*3+1),buffer.get(27*3+2));

            Node nodeDownLips = new Node();
            Node nodeUpLips = new Node();

            Node nodeDownREye = new Node();
            Node nodeUpREye = new Node();

            nodeDownLips.setLocalPosition(vectorDownLips);
            nodeUpLips.setLocalPosition(vectorUpLips);
            nodeDownREye.setLocalPosition(vectorDownRightEye);
            nodeUpREye.setLocalPosition(vectorUpRightEye);

            nodeDownLips.setParent(faceNodeMap.get(face));
            nodeUpLips.setParent(faceNodeMap.get(face));
            nodeDownREye.setParent(faceNodeMap.get(face));
            nodeUpREye.setParent(faceNodeMap.get(face));

            distanceBetweenLips = distance(vectorDownLips,vectorUpLips);
            distanceBetweenREye = distance(vectorDownRightEye,vectorUpRightEye);
            //Log.d("Reye",String.valueOf(distanceBetweenREye));
          }

          // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
          Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter =
              faceNodeMap.entrySet().iterator();
          while (iter.hasNext()) {
            Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
            AugmentedFace face = entry.getKey();
            centerPose = face.getCenterPose();
            if (face.getTrackingState() == TrackingState.STOPPED) {
              AugmentedFaceNode faceNode = entry.getValue();
              faceNode.setParent(null);
              iter.remove();
            }
          }
          Frame frame = arFragment.getArSceneView().getArFrame();

          if (centerPose!= null) {

            Pose cameraPose = frame.getCamera().getPose();

            float dx = centerPose.tx() - cameraPose.tx();
            float dy = centerPose.ty() - cameraPose.ty();
            float dz = centerPose.tz() - cameraPose.tz();

            //Compute the straight-line distance.
            float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
            //tv.setText(String.valueOf(distance));
            if(distance > 0.35f) {tv.setText("too far"); StateHolder.getInstance().setState("far");}
            else if(distance < 0.25f) {tv.setText("too close (but too pretty)"); StateHolder.getInstance().setState("close");}
            else if(Math.abs(centerPose.qw()) < 0.990f && Math.abs(centerPose.qy()) > 0.130f){tv.setText("bad yaw"); StateHolder.getInstance().setState("bad yaw");}
            else if(Math.abs(centerPose.qw()) < 0.990f && Math.abs(centerPose.qz()) > 0.130f) {tv.setText("bad pitch"); StateHolder.getInstance().setState("bad pitch");}
            else if(Math.abs(centerPose.qw()) < 0.990f && Math.abs(centerPose.qx()) > 0.130f) {tv.setText("bad roll"); StateHolder.getInstance().setState("bad roll");}
            else if(distanceBetweenLips > 0.05f){tv.setText("mouse open"); StateHolder.getInstance().setState("mouse open");}
            else{tv.setText("good"); StateHolder.getInstance().setState("good");}

        }});
  }
  private float distance(Vector3 vectorA, Vector3 vectorB){
    float x = vectorA.x - vectorB.x;
    float y = vectorA.y - vectorB.y;
    float z = vectorA.z - vectorB.z;
    return (float) Math.sqrt(x*x+y*y+z*z);
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
  public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
    if (ArCoreApk.getInstance().checkAvailability(activity)
        == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
      Log.e(TAG, "Augmented Faces requires ARCore.");
      Toast.makeText(activity, "Augmented Faces requires ARCore", Toast.LENGTH_LONG).show();
      activity.finish();
      return false;
    }
    String openGlVersionString =
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
          .show();
      activity.finish();
      return false;
    }
    return true;
  }
}
