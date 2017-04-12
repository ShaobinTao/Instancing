/*
 * Copyright (C) 2011 The Android Open Source Project
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
package com.example.android.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Shower {

    public Shower() {
        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram2 = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram2, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram2, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram2);                  // create OpenGL program executables

        MyGLRenderer.checkGlError("t1");

        int vertexShaderBG = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vsBG);
        int fragmentShaderBG = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fsBG);
        mProgramBG = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgramBG, vertexShaderBG);   // add the vertex shader to program
        GLES20.glAttachShader(mProgramBG, fragmentShaderBG); // add the fragment shader to program
        GLES20.glLinkProgram(mProgramBG);                  // create OpenGL program executables

        MyGLRenderer.checkGlError("t1");


        GLES20.glUseProgram(mProgram2);

        MyGLRenderer.checkGlError("t1");


        mSampler = GLES20.glGetUniformLocation(mProgram2, "uTexture");
        MyGLRenderer.checkGlError("t1");
        muAlpha = GLES20.glGetUniformLocation(mProgram2, "uAlpha");
        MyGLRenderer.checkGlError("t1");
        muInfluence = GLES20.glGetUniformLocation(mProgram2, "uInfluence");
        MyGLRenderer.checkGlError("t1");


        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram2, "aTextureCoord");
        mVertCoordHandle = GLES20.glGetAttribLocation(mProgram2, "aVertexCoord");
        muMVPMatrixHandle = GLES20.glGetAttribLocation(mProgram2, "uMVPMatrix");
        maFSParamsHandle = GLES20.glGetAttribLocation(mProgram2, "aFSParams");

        MyGLRenderer.checkGlError("t1");

        mBGVertexCoordHandle = GLES20.glGetAttribLocation(mProgramBG, "aVertexCoord");
        mBGVertexColorHandle = GLES20.glGetAttribLocation(mProgramBG, "aVertexColor");

        //bg
        mBGMVPMatrix = GLES20.glGetUniformLocation(mProgramBG, "uMVPMatrix");
        MyGLRenderer.checkGlError("t1");
        uBGRandomMultiple = GLES20.glGetUniformLocation(mProgramBG, "uRandomMultiple");
        MyGLRenderer.checkGlError("t1");


        GLES20.glGenBuffers(1, mTexCoordBuf, 0);
        GLES20.glGenBuffers(1, mVertCoordBuf, 0);
        GLES20.glGenBuffers(1, mIndexBuf, 0);
        GLES20.glGenBuffers(1, mMatrixBuf, 0);
        GLES20.glGenBuffers(1, maFSParamsBuf, 0);

        GLES20.glGenBuffers(1, mBGaVertexCoordBuf, 0);
        GLES20.glGenBuffers(1, mBGaVertexColorBuf, 0);
        GLES20.glGenBuffers(1, mBGIndexBuf, 0);

        MyGLRenderer.checkGlError("t1");

        // texture coordinates buffer
        {
            FloatBuffer textureBuffer;
            ByteBuffer buf = ByteBuffer.allocateDirect(texCoords.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            textureBuffer = buf.asFloatBuffer();
            textureBuffer.put(texCoords);
            textureBuffer.position(0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, textureBuffer.capacity() * 4, textureBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        // vertex buffer
        {
            FloatBuffer vertBuffer;
            ByteBuffer buf = ByteBuffer.allocateDirect(vertCoords.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            vertBuffer = buf.asFloatBuffer();
            vertBuffer.put(vertCoords);
            vertBuffer.position(0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertBuffer.capacity() * 4, vertBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        // index buffer
        {
            IntBuffer indexBuffer;
            ByteBuffer buf = ByteBuffer.allocateDirect(vertIndex.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            indexBuffer = buf.asIntBuffer();
            indexBuffer.put(vertIndex);
            indexBuffer.position(0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.capacity() * 4, indexBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        // matrix buffer
        {
            FloatBuffer matBuffer;
            ByteBuffer buf = ByteBuffer.allocateDirect(matricesValues.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            matBuffer = buf.asFloatBuffer();
            matBuffer.put(matricesValues);
            matBuffer.position(0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mMatrixBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, matBuffer.capacity() * 4, matBuffer, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");

        // aFSParams buffer
        {
            FloatBuffer tmpBuf;
            ByteBuffer buf = ByteBuffer.allocateDirect(mFSParamsValues.length * 4 );
            buf.order(  ByteOrder.nativeOrder() );
            tmpBuf = buf.asFloatBuffer();
            tmpBuf.put(mFSParamsValues);
            tmpBuf.position(0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, maFSParamsBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, tmpBuf.capacity() * 4, tmpBuf, GLES20.GL_STATIC_DRAW );
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");

        // bg buf1
        {
            FloatBuffer bgaVertexCoord;
            ByteBuffer buf = ByteBuffer.allocateDirect(bgBuf1.length * 4);
            buf.order(ByteOrder.nativeOrder());
            bgaVertexCoord = buf.asFloatBuffer();
            bgaVertexCoord.put(bgBuf1);
            bgaVertexCoord.position(0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mBGaVertexCoordBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, bgaVertexCoord.capacity() * 4, bgaVertexCoord, GLES20.GL_STATIC_DRAW);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");

        // bg buf3
        {
            FloatBuffer bgaVertexColor;
            ByteBuffer buf = ByteBuffer.allocateDirect(bgBuf3.length * 4);
            buf.order(ByteOrder.nativeOrder());
            bgaVertexColor = buf.asFloatBuffer();
            bgaVertexColor.put(bgBuf3);
            bgaVertexColor.position(0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mBGaVertexColorBuf[0]);
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, bgaVertexColor.capacity() * 4, bgaVertexColor, GLES20.GL_STATIC_DRAW);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");

        {
            IntBuffer bgIndexBuffer;
            ByteBuffer buf = ByteBuffer.allocateDirect(bgBuf4.length * 4);
            buf.order(ByteOrder.nativeOrder());
            bgIndexBuffer = buf.asIntBuffer();
            bgIndexBuffer.put(bgBuf4);
            bgIndexBuffer.position(0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mBGIndexBuf[0]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, bgIndexBuffer.capacity() * 4, bgIndexBuffer, GLES20.GL_STATIC_DRAW);
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        }
        MyGLRenderer.checkGlError("t1");

    }

    public void draw() {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)                                                                                                                                                                                                              ; // 225392
        GLES20.glDepthFunc(GLES20.GL_LESS)                                                                                                                                                                                                                ; // 225393
        GLES20.glDepthMask(true)                                                                                                                                                                                                                ; // 225394
        GLES20.glClearDepthf(1.0f)                                                                                                                                                                                                                     ; // 225395
        GLES20.glEnable(GLES20.GL_CULL_FACE)                                                                                                                                                                                                               ; // 225397
        GLES20.glCullFace(GLES20.GL_BACK)                                                                                                                                                                                                                 ; // 225398
        GLES20.glFrontFace(GLES20.GL_CCW)                                                                                                                                                                                                                 ; // 225399
        GLES20.glEnable(GLES20.GL_BLEND)                                                                                                                                                                                                                   ; // 225400
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE)                                                                                                                                                                                        ; // 225401
        GLES20.glDisable(GLES20.GL_DEPTH_TEST)                                                                                                                                                                                                             ; // 225402
        GLES20.glDepthMask(false)                                                                                                                                                                                                               ; // 225403

        GLES20.glUseProgram(mProgramBG)                                                                                                                                                     ;//195449
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mBGaVertexCoordBuf[0])                                                                                                                              ;//195450
        GLES20.glEnableVertexAttribArray(mBGVertexCoordHandle)                                                                                                                                          ;//195451
        GLES20.glVertexAttribPointer(mBGVertexCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0)                                                                          ;//195452
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mBGaVertexColorBuf[0])                                                                                                                              ;//195453

        GLES20.glEnableVertexAttribArray(mBGVertexColorHandle)                                                                                                                                          ;//195454
        GLES20.glVertexAttribPointer(mBGVertexColorHandle, 4, GLES20.GL_FLOAT, false, 0, 0)                                                                          ;//195455

        GLES20.glUniform1f(uBGRandomMultiple, 2.0f)                                                                                                                                             ;//195456
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)                                                                                                                              ;//195457
        {
            float value[] = {1.7777778f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.016806724f, 0.0f, 0.0f, 0.0f, -0.94957983f, 1.0f};
            GLES20.glUniformMatrix4fv(mBGMVPMatrix, 1, false, value, 0);//195476
        }
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mBGIndexBuf[0])                                                                                                                      ;//195459
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6,    GLES20.GL_UNSIGNED_INT, 0);



// instance 1
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)                                                                                                                                                                                   ; // 225529
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)                                                                                                                                                                                           ; // 225530
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)                                                                                                                                                                                           ; // 225531
        GLES20.glUseProgram(mProgram2)                                                                                                                                                                                                                 ; // 225532
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)                                                                                                                                                                                                     ; // 225533
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glUniform1i(mSampler,0)                                                                                                                                                                                                            ; // 225535
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mTexCoordBuf[0])                                                                                                                                                                                          ; // 225536
        GLES20.glEnableVertexAttribArray(mTexCoordHandle)                                                                                                                                                                                                       ; // 225537
        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, 0)                                                                                                                                       ; // 225538
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVertCoordBuf[0])                                                                                                                                                                                          ; // 225539
        GLES20.glEnableVertexAttribArray(mVertCoordHandle)                                                                                                                                                                                                       ; // 225540
        GLES20.glVertexAttribPointer(mVertCoordHandle, 3, GLES20.GL_FLOAT, false, 0, 0)                                                                                                                                       ; // 225541

        // bind matrix
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mMatrixBuf[0])                                                                                                                                                                                          ; // 225539
        GLES20.glEnableVertexAttribArray(muMVPMatrixHandle)                                                                                                                                                                                                       ; // 225540
        GLES20.glVertexAttribPointer(muMVPMatrixHandle, 4, GLES20.GL_FLOAT, false, 4*4 * 4, 0)                                                                                                                                       ; // 225541
        GLES30.glVertexAttribDivisor(muMVPMatrixHandle, 1);

        GLES20.glEnableVertexAttribArray(muMVPMatrixHandle+1)                                                                                                                                                                                                       ; // 225540
        GLES20.glVertexAttribPointer(muMVPMatrixHandle+1, 4, GLES20.GL_FLOAT, false, 4*4 * 4, 4*4*1)                                                                                                                                       ; // 225541
        GLES30.glVertexAttribDivisor(muMVPMatrixHandle+1, 1);

        GLES20.glEnableVertexAttribArray(muMVPMatrixHandle+2)                                                                                                                                                                                                       ; // 225540
        GLES20.glVertexAttribPointer(muMVPMatrixHandle+2, 4, GLES20.GL_FLOAT, false, 4*4 * 4, 4*4*2)                                                                                                                                       ; // 225541
        GLES30.glVertexAttribDivisor(muMVPMatrixHandle+2, 1);

        GLES20.glEnableVertexAttribArray(muMVPMatrixHandle+3)                                                                                                                                                                                                       ; // 225540
        GLES20.glVertexAttribPointer(muMVPMatrixHandle+3, 4, GLES20.GL_FLOAT, false, 4*4 * 4, 4*4*3)                                                                                                                                       ; // 225541
        GLES30.glVertexAttribDivisor(muMVPMatrixHandle+3, 1);

        //bind aFSParams
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, maFSParamsBuf[0])                                                                                                                                                                                          ; // 225539
        GLES20.glEnableVertexAttribArray(maFSParamsHandle)                                                                                                                                                                                                       ; // 225540
        GLES20.glVertexAttribPointer(maFSParamsHandle, 2, GLES20.GL_FLOAT, false, 0, 0)                                                                                                                                       ; // 225541
        GLES30.glVertexAttribDivisor(maFSParamsHandle, 1);


//        GLES20.glUniform1f(muAlpha, 0.32013953f)                                                                                                                                                                                                   ; // 225542
//        GLES20.glUniform1f(muInfluence, 0.3f)                                                                                                                                                                                                          ; // 225543


        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mIndexBuf[0])                                                                                                                                                                                  ; // 225546
        GLES30.glDrawElementsInstanced(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0, 100/*primcount*/)                                                                                                                                                           ; // 225547


        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)      ; // 227429
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)              ; // 227430
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)              ; // 227431
        GLES20.glDisable(GLES20.GL_BLEND)                                     ; // 227432
        GLES20.glDisable(GLES20.GL_CULL_FACE)                                 ; // 227433
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)                                 ; // 227434
        GLES20.glDepthFunc(GLES20.GL_LESS)                                   ; // 227435
        GLES20.glDepthMask(false)                                  ; // 227436
    }


    public void loadTexture(Context context) {
        InputStream imageStream = context.getResources().openRawResource(  R.drawable.snow );

        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (Exception e) {

        } finally {
            try {
                imageStream.close();
                imageStream = null;
            } catch (Exception e) {

            }
        }

        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
    }

    private final String vertexShaderCode =
            "attribute mat4 uMVPMatrix;                       " +
                    "attribute vec2 aTextureCoord;                  " +
                    "attribute vec4 aVertexCoord;                   " +
                    "attribute vec2 aFSParams;                   " +

                    "varying vec2 vTextureCoord;                    " +
                    "varying vec2 vFSParams;                        " +
                    "                                               " +
                    "void main() {                                  " +
                    "    gl_Position = uMVPMatrix * aVertexCoord;   " +
                    "    vTextureCoord = aTextureCoord;             " +
                    "    vFSParams = aFSParams;                     " +
                    "}";

    private final String fragmentShaderCode = "" +
            "precision highp float;                                                              " +
            "const float ONE = 1.0;                                                              " +
//            "uniform float uAlpha;                                                             " +
//            "uniform float uInfluence;                                                         " +
            "uniform sampler2D uTexture;                                                         " +
            "varying vec2 vTextureCoord;                                                         " +
            "varying vec2 vFSParams;                                                             " +
            "void main() {                                                                       " +
            "    vec4 color = texture2D(uTexture, vTextureCoord);                                " +
            "    gl_FragColor = vec4(color.rgb, (color.a )*vFSParams.x);  " +
            "}";

    private final String vsBG =
            " uniform mat4 uMVPMatrix;                   " +
                    " attribute vec4 aVertexCoord;               " +
                    " attribute vec4 aVertexColor;               " +
                    " varying vec4 vVertexColor;                 " +
                    "                                            " +
                    " void main() {                              " +
                    "   gl_Position = uMVPMatrix * aVertexCoord; " +
                    "   vVertexColor = aVertexColor;             " +
                    "}";

    private final String fsBG =
            " precision highp float;                                  " +
                    " const float COLOR = 255.0;                              " +
                    " const vec2 CVEC = vec2(12.9898,78.233);                 " +
                    " const float FACTOR = 43758.5453;                        " +
                    " uniform float uRandomMultiple;                          " +
                    " varying vec4 vVertexColor;                              " +
                    "                                                         " +
                    " float rand(vec2 v) {                                    " +
                    "   return fract(sin(dot(v.xy ,CVEC)) * FACTOR);          " +
                    " }                                                       " +
                    "                                                         " +
                    " vec4 randomDither() {                                   " +
                    "   float random = rand(gl_FragCoord.xy)*uRandomMultiple/COLOR; " +
                    "   vec4 o = vVertexColor;  " +
                    "   o.rgb += random;        " +
                    "   return o;               " +
                    " }                                                       " +
                    "                                                         " +
                    " void main() {                                           " +
                    "   vec4 color = randomDither();                          " +
                    "   gl_FragColor = vec4(color.rgb, color.a);              " +
                    "}";



    private final int mProgramBG;
    private final int mProgram2;
    private int[] textures = new int[1];

    int   mSampler;
    int   muAlpha;
    int   muInfluence;

    private int mTexCoordHandle;
    private int mVertCoordHandle;
    private int muMVPMatrixHandle;
    private int maFSParamsHandle;

    int   mBGMVPMatrix;
    int   uBGRandomMultiple;


    private int[] mTexCoordBuf = new int[1];
    private int[] mVertCoordBuf = new int[1];
    private int[] mIndexBuf = new int[1];
    private int[] mMatrixBuf = new int[1];
    private int[] maFSParamsBuf = new int[1];


    // texture coordinates
    private float texCoords[] = {        1,      1,        1,     0,        0,      1,        0,     0 };
    // 2 floats
    static int texStride = 2 * 4;

    // vert coord
///*  staotemp
    private float vertCoords[] = {
            -0.01f,     -0.01f,     0,
            -0.01f,     0.01f,     0,
            0.01f,     -0.01f,     0,
            0.01f,     0.01f,     0 };
//*/
/*
    private float vertCoords[] = {
            -1f,     -1,     0,
            -1,     1f,     0,
            1,     -1,     0,
            1,     1f,     0 };
*/

    // vert indices
    private int vertIndex[] = {
            2, 0, 3, 3, 0, 1
    };


    // matrix buffer
/*    private float matricesValues[] = {
        -4.291935f, 0.0f, 8.8892065E-8f, 8.742278E-8f, 0.0f, 2.4142134f, 0.0f, 0.0f, 3.7521286E-7f, 0.0f, 1.0168067f, 1.0f, -1.3661366f, 1.1819212f, 0.2156434f, 2.1955502f,
        -4.291935f, 0.0f, 8.8892065E-8f, 8.742278E-8f, 10.0f, 12.4142134f, 10.0f, 10.0f, 3.7521286E-7f, 0.0f, 1.0168067f, 10.0f, -1.3661366f, 1.1819212f, 0.2156434f, 20.1955502f
    };
*/
    private float matricesValues[] = {
            -3.448004f, 0.0f, 7.141306E-8f, 7.023268E-8f, 0.0f, 1.9395024f, 0.0f, 0.0f, 3.0143408E-7f, 0.0f, 0.8168702f, 0.8033682f, 1.5441458f, -0.4605259f, -0.5388384f, 1.4535393f,
            -2.704262f, 0.0f, 5.6009107E-8f, 5.5083337E-8f, 0.0f, 1.5211474f, 0.0f, 0.0f, 2.364141E-7f, 0.0f, 0.6406695f, 0.6300799f, 1.3644304f, -1.0146023f, -0.7085412f, 1.2866414f,
            -5.3999195f, 0.0f, 1.1184E-7f, 1.09991404E-7f, 0.0f, 3.0374546f, 0.0f, 0.0f, 4.7207595E-7f, 0.0f, 1.2793005f, 1.258155f, 2.3378654f, -0.7154785f, -0.4067933f, 1.5834017f,
            -4.921385f, 0.0f, 1.0192887E-7f, 1.00244094E-7f, 0.0f, 2.768279f, 0.0f, 0.0f, 4.3024113E-7f, 0.0f, 1.1659304f, 1.1466588f, -0.6368395f, -0.7123382f, -0.71632874f, 1.2789826f,
            -5.9086285f, 0.0f, 1.2237608E-7f, 1.2035333E-7f, 0.0f, 3.3236034f, 0.0f, 0.0f, 5.165487E-7f, 0.0f, 1.3998191f, 1.3766817f, 1.8483113f, -1.2733526f, -0.87007695f, 1.1277757f,
            -3.6110642f, 0.0f, 7.4790265E-8f, 7.355406E-8f, 0.0f, 2.0312235f, 0.0f, 0.0f, 3.1568925E-7f, 0.0f, 0.8555009f, 0.8413604f, -1.6936097f, -0.9446447f, -0.9700728f, 1.0294327f,
            -6.3844686f, 0.0f, 1.322314E-7f, 1.3004576E-7f, 0.0f, 3.5912635f, 0.0f, 0.0f, 5.58148E-7f, 0.0f, 1.512551f, 1.4875501f, 1.1042104f, -1.2828814f, -0.82732713f, 1.1698189f,
            -4.9445367f, 0.0f, 1.0240837E-7f, 1.00715674E-7f, 0.0f, 2.7813017f, 0.0f, 0.0f, 4.3226513E-7f, 0.0f, 1.1714152f, 1.152053f, -0.623665f, -1.0593337f, -0.5756321f, 1.4173536f,
            -5.347296f, 0.0f, 1.107501E-7f, 1.0891952E-7f, 0.0f, 3.0078542f, 0.0f, 0.0f, 4.674755E-7f, 0.0f, 1.2668334f, 1.2458941f, -0.34178036f, -1.0104046f, -0.5494008f, 1.4431514f,
            -4.792669f, 0.0f, 9.926298E-8f, 9.762227E-8f, 0.0f, 2.6958764f, 0.0f, 0.0f, 4.1898844E-7f, 0.0f, 1.1354362f, 1.1166686f, 0.2155473f, -0.63557583f, -0.60786486f, 1.3856536f,
            -5.442039f, 0.0f, 1.1271236E-7f, 1.1084934E-7f, 0.0f, 3.061147f, 0.0f, 0.0f, 4.7575816E-7f, 0.0f, 1.2892791f, 1.2679687f, 1.7858708f, -1.1731622f, -0.4813618f, 1.5100658f,
            -5.6196833f, 0.0f, 1.16391625E-7f, 1.14467795E-7f, 0.0f, 3.161072f, 0.0f, 0.0f, 4.9128835E-7f, 0.0f, 1.331365f, 1.309359f, -0.26828724f, -0.9716191f, -0.8178932f, 1.1790969f,
            -3.9892204f, 0.0f, 8.262242E-8f, 8.125676E-8f, 0.0f, 2.2439365f, 0.0f, 0.0f, 3.4874873E-7f, 0.0f, 0.9450903f, 0.929469f, -2.0451043f, -1.1283842f, -0.85666174f, 1.1409692f,
            -6.2694225f, 0.0f, 1.2984864E-7f, 1.2770238E-7f, 0.0f, 3.52655f, 0.0f, 0.0f, 5.480903E-7f, 0.0f, 1.4852953f, 1.460745f, -1.7442832f, -0.5542459f, -0.3574301f, 1.631949f,
            -6.0585814f, 0.0f, 1.2548182E-7f, 1.2340774E-7f, 0.0f, 3.407952f, 0.0f, 0.0f, 5.29658E-7f, 0.0f, 1.4353447f, 1.41162f, 1.0842477f, -0.6546675f, -0.48547047f, 1.5060251f,
            -4.1616697f, 0.0f, 8.619409E-8f, 8.47694E-8f, 0.0f, 2.3409393f, 0.0f, 0.0f, 3.6382474E-7f, 0.0f, 0.98594546f, 0.96964884f, -0.4510988f, -1.6838006f, -0.9052209f, 1.0932126f,
            -5.5194063f, 0.0f, 1.1431474E-7f, 1.1242524E-7f, 0.0f, 3.104666f, 0.0f, 0.0f, 4.8252184E-7f, 0.0f, 1.3076082f, 1.2859949f, 0.39781964f, -1.0951884f, -0.8032664f, 1.1934819f,
            -2.594712f, 0.0f, 5.374017E-8f, 5.2851902E-8f, 0.0f, 1.4595256f, 0.0f, 0.0f, 2.2683693E-7f, 0.0f, 0.6147159f, 0.6045553f, 0.89745814f, -1.7449183f, -0.9148724f, 1.0837207f,
            -2.3964262f, 0.0f, 4.963339E-8f, 4.8813003E-8f, 0.0f, 1.3479898f, 0.0f, 0.0f, 2.0950223E-7f, 0.0f, 0.56773984f, 0.5583557f, -0.7864393f, -1.284014f, -0.89747274f, 1.1008327f,
            -2.455189f, 0.0f, 5.0850453E-8f, 5.0009948E-8f, 0.0f, 1.3810438f, 0.0f, 0.0f, 2.1463944E-7f, 0.0f, 0.58166134f, 0.5720471f, -2.0885897f, -1.2933292f, -0.9423803f, 1.0566674f,
            -5.3817625f, 0.0f, 1.1146394E-7f, 1.0962156E-7f, 0.0f, 3.0272412f, 0.0f, 0.0f, 4.704886E-7f, 0.0f, 1.2749989f, 1.2539245f, -0.2591625f, -0.8737411f, -0.36640579f, 1.6231217f,
            -3.523779f, 0.0f, 7.2982466E-8f, 7.177614E-8f, 0.0f, 1.9821255f, 0.0f, 0.0f, 3.0805853E-7f, 0.0f, 0.83482206f, 0.82102334f, -0.33023858f, -2.035648f, -0.9446986f, 1.0543875f,
            -3.5294998f, 0.0f, 7.310095E-8f, 7.189267E-8f, 0.0f, 1.9853436f, 0.0f, 0.0f, 3.0855867E-7f, 0.0f, 0.8361774f, 0.8223563f, 0.28764144f, -1.4592603f, -0.37016642f, 1.6194232f,
            -4.273367f, 0.0f, 8.8507505E-8f, 8.704457E-8f, 0.0f, 2.403769f, 0.0f, 0.0f, 3.7358964E-7f, 0.0f, 1.0124078f, 0.9956738f, -2.4461222f, -1.1648847f, -0.5901118f, 1.4031134f,
            -4.3430424f, 0.0f, 8.995058E-8f, 8.846379E-8f, 0.0f, 2.4429615f, 0.0f, 0.0f, 3.7968084E-7f, 0.0f, 1.0289147f, 1.0119078f, -0.7561635f, -1.1808741f, -0.56482255f, 1.4279845f,
            -4.9469132f, 0.0f, 1.024576E-7f, 1.0076408E-7f, 0.0f, 2.7826385f, 0.0f, 0.0f, 4.324729E-7f, 0.0f, 1.1719782f, 1.1526067f, -1.4647918f, -1.3173944f, -0.7695464f, 1.2266445f,
            -6.185081f, 0.0f, 1.2810182E-7f, 1.2598443E-7f, 0.0f, 3.479108f, 0.0f, 0.0f, 5.40717E-7f, 0.0f, 1.4653139f, 1.4410938f, -1.4597706f, -1.508658f, -0.9465922f, 1.0525252f,
            -4.461507f, 0.0f, 9.2404136E-8f, 9.0876796E-8f, 0.0f, 2.5095975f, 0.0f, 0.0f, 3.900373E-7f, 0.0f, 1.0569801f, 1.0395094f, -0.09472076f, -1.255005f, -0.641624f, 1.3524525f,
            -4.5295186f, 0.0f, 9.381276E-8f, 9.2262134E-8f, 0.0f, 2.5478542f, 0.0f, 0.0f, 3.9598308E-7f, 0.0f, 1.0730928f, 1.0553558f, -0.4295949f, -1.5015948f, -0.88585997f, 1.1122535f,
            -4.211106f, 0.0f, 8.7217984E-8f, 8.5776364E-8f, 0.0f, 2.3687472f, 0.0f, 0.0f, 3.6814657E-7f, 0.0f, 0.9976575f, 0.98116726f, -0.75089306f, -2.1457195f, -0.8783734f, 1.1196164f,
            -2.400955f, 0.0f, 4.972719E-8f, 4.8905253E-8f, 0.0f, 1.3505372f, 0.0f, 0.0f, 2.0989816E-7f, 0.0f, 0.5688127f, 0.55941087f, 0.8017695f, -1.287189f, -0.61080265f, 1.3827645f,
            -3.5885818f, 0.0f, 7.4324625E-8f, 7.309612E-8f, 0.0f, 2.0185773f, 0.0f, 0.0f, 3.1372377E-7f, 0.0f, 0.8501746f, 0.83612216f, -0.5659288f, -2.3314667f, -0.92954993f, 1.0692858f,
            -4.150712f, 0.0f, 8.5967145E-8f, 8.45462E-8f, 0.0f, 2.3347757f, 0.0f, 0.0f, 3.6286679E-7f, 0.0f, 0.9833495f, 0.9670958f, -1.5765293f, -1.9863242f, -0.5623602f, 1.4304062f,
            -4.0424595f, 0.0f, 8.372508E-8f, 8.234119E-8f, 0.0f, 2.2738833f, 0.0f, 0.0f, 3.5340304E-7f, 0.0f, 0.9577032f, 0.9418734f, -0.16681656f, -1.6156343f, -0.86262745f, 1.135102f,
            -3.0232947f, 0.0f, 6.261672E-8f, 6.158174E-8f, 0.0f, 1.7006034f, 0.0f, 0.0f, 2.6430482E-7f, 0.0f, 0.71625185f, 0.704413f, -2.090995f, -1.473661f, -0.7315135f, 1.2640488f,
            -3.6142788f, 0.0f, 7.485684E-8f, 7.361954E-8f, 0.0f, 2.0330317f, 0.0f, 0.0f, 3.1597028E-7f, 0.0f, 0.8562625f, 0.8421094f, 0.4186643f, -1.2310399f, -0.47975153f, 1.5116494f,
            -6.3239965f, 0.0f, 1.3097895E-7f, 1.2881401E-7f, 0.0f, 3.5572479f, 0.0f, 0.0f, 5.5286137E-7f, 0.0f, 1.4982245f, 1.4734604f, 2.1201174f, -1.7868905f, -0.44726408f, 1.5435998f,
            -3.726449f, 0.0f, 7.718005E-8f, 7.5904346E-8f, 0.0f, 2.0961275f, 0.0f, 0.0f, 3.257765E-7f, 0.0f, 0.8828369f, 0.8682445f, 1.0926545f, 1.7348595f, -0.3664838f, 1.623045f,
            -3.627587f, 0.0f, 7.513248E-8f, 7.3890625E-8f, 0.0f, 2.0405178f, 0.0f, 0.0f, 3.1713375E-7f, 0.0f, 0.8594154f, 0.8452102f, -1.6543354f, 1.222886f, -0.8209008f, 1.176139f,
            -3.4663203f, 0.0f, 7.1792414E-8f, 7.0605765E-8f, 0.0f, 1.9498051f, 0.0f, 0.0f, 3.0303536E-7f, 0.0f, 0.8212095f, 0.8076358f, 0.57946706f, 1.2101527f, -0.791271f, 1.2052791f,
            -5.72721f, 0.0f, 1.18618644E-7f, 1.1665801E-7f, 0.0f, 3.2215557f, 0.0f, 0.0f, 5.006886E-7f, 0.0f, 1.3568392f, 1.3344121f, -1.3320563f, 1.2458053f, -0.6611387f, 1.3332604f,
            -2.2096784f, 0.0f, 4.5765578E-8f, 4.5009124E-8f, 0.0f, 1.2429441f, 0.0f, 0.0f, 1.9317623E-7f, 0.0f, 0.5234972f, 0.51484436f, 1.5083624f, 1.1868657f, -0.7039317f, 1.2911748f,
            -3.3008595f, 0.0f, 6.836548E-8f, 6.7235476E-8f, 0.0f, 1.8567334f, 0.0f, 0.0f, 2.8857028E-7f, 0.0f, 0.78201f, 0.7690842f, -1.4466846f, 1.2346501f, -0.5965346f, 1.3967967f,
            -2.9464712f, 0.0f, 6.10256E-8f, 6.0016916E-8f, 0.0f, 1.6573901f, 0.0f, 0.0f, 2.575887E-7f, 0.0f, 0.6980515f, 0.6865135f, 1.2234628f, 1.3437438f, -0.4863823f, 1.5051283f,
            -2.9618034f, 0.0f, 6.134316E-8f, 6.032922E-8f, 0.0f, 1.6660144f, 0.0f, 0.0f, 2.589291E-7f, 0.0f, 0.70168394f, 0.6900858f, -0.42710093f, 0.8544474f, -0.9669202f, 1.0325332f,
            -3.472362f, 0.0f, 7.191754E-8f, 7.0728824E-8f, 0.0f, 1.9532036f, 0.0f, 0.0f, 3.0356352E-7f, 0.0f, 0.82264084f, 0.80904347f, 0.9273309f, 0.8025028f, -0.9061512f, 1.0922977f,
            -6.2058177f, 0.0f, 1.2853128E-7f, 1.264068E-7f, 0.0f, 3.4907725f, 0.0f, 0.0f, 5.425298E-7f, 0.0f, 1.4702266f, 1.4459254f, 0.87766373f, 0.7197689f, -0.9706625f, 1.0288527f,
            -5.368183f, 0.0f, 1.1118269E-7f, 1.0934496E-7f, 0.0f, 3.019603f, 0.0f, 0.0f, 4.6930145E-7f, 0.0f, 1.2717817f, 1.2507606f, -0.18039154f, 1.2532508f, -0.51865625f, 1.4733877f,
            -6.317045f, 0.0f, 1.3083498E-7f, 1.2867241E-7f, 0.0f, 3.553338f, 0.0f, 0.0f, 5.522536E-7f, 0.0f, 1.4965776f, 1.4718409f, -0.5189864f, 1.1554247f, -0.5290933f, 1.4631232f,
            -3.9562087f, 0.0f, 8.19387E-8f, 8.058434E-8f, 0.0f, 2.2253675f, 0.0f, 0.0f, 3.4586276E-7f, 0.0f, 0.9372695f, 0.9217774f, 0.6506373f, 0.94779027f, -0.76195264f, 1.2341129f,
            -5.571315f, 0.0f, 1.1538984E-7f, 1.1348257E-7f, 0.0f, 3.1338646f, 0.0f, 0.0f, 4.870598E-7f, 0.0f, 1.3199059f, 1.2980893f, -0.49257448f, 0.9171572f, -0.5676948f, 1.4251598f,
            -4.1822267f, 0.0f, 8.6619856E-8f, 8.518812E-8f, 0.0f, 2.3525026f, 0.0f, 0.0f, 3.6562187E-7f, 0.0f, 0.9908156f, 0.9744385f, 0.46172222f, 0.7438322f, -0.68576026f, 1.3090458f,
            -4.405768f, 0.0f, 9.124971E-8f, 8.974145E-8f, 0.0f, 2.4782445f, 0.0f, 0.0f, 3.8516447E-7f, 0.0f, 1.043775f, 1.0265225f, 0.016077936f, 0.71749103f, -0.73796237f, 1.2577065f,
            -5.485176f, 0.0f, 1.13605786E-7f, 1.11728006E-7f, 0.0f, 3.0854115f, 0.0f, 0.0f, 4.795293E-7f, 0.0f, 1.2994988f, 1.2780194f, 0.7011396f, 0.787623f, -0.64863694f, 1.3455555f,
            -2.6209154f, 0.0f, 5.428288E-8f, 5.338564E-8f, 0.0f, 1.4742649f, 0.0f, 0.0f, 2.2912769E-7f, 0.0f, 0.62092376f, 0.61066055f, -2.0312307f, 0.7822354f, -0.756161f, 1.2398087f,
            -3.944391f, 0.0f, 8.169394E-8f, 8.0343625E-8f, 0.0f, 2.21872f, 0.0f, 0.0f, 3.448296E-7f, 0.0f, 0.9344697f, 0.91902393f, 0.14108618f, 0.90804785f, -0.6218183f, 1.3719308f,
            -2.6701548f, 0.0f, 5.5302696E-8f, 5.43886E-8f, 0.0f, 1.5019621f, 0.0f, 0.0f, 2.3343233E-7f, 0.0f, 0.6325891f, 0.6221331f, -0.027143808f, 0.6215466f, -0.742193f, 1.2535459f,
            -5.4825397f, 0.0f, 1.1355118E-7f, 1.11674304E-7f, 0.0f, 3.0839286f, 0.0f, 0.0f, 4.792989E-7f, 0.0f, 1.2988741f, 1.2774051f, -0.57708675f, 0.81510717f, -0.6316068f, 1.3623042f,
            -5.521674f, 0.0f, 1.1436171E-7f, 1.1247143E-7f, 0.0f, 3.1059415f, 0.0f, 0.0f, 4.8272005E-7f, 0.0f, 1.3081454f, 1.2865232f, -0.24459909f, 0.7929198f, -0.6304419f, 1.3634498f,
            -3.6454585f, 0.0f, 7.550262E-8f, 7.425464E-8f, 0.0f, 2.0505705f, 0.0f, 0.0f, 3.186961E-7f, 0.0f, 0.8636493f, 0.8493741f, -0.9786785f, 0.4340207f, -0.98347276f, 1.0162542f,
            -4.5748305f, 0.0f, 9.4751236E-8f, 9.31851E-8f, 0.0f, 2.5733423f, 0.0f, 0.0f, 3.9994438E-7f, 0.0f, 1.0838279f, 1.0659133f, 1.6587468f, 0.6852814f, -0.6014631f, 1.3919497f,
            -3.1537404f, 0.0f, 6.531844E-8f, 6.42388E-8f, 0.0f, 1.773979f, 0.0f, 0.0f, 2.7570874E-7f, 0.0f, 0.74715585f, 0.7348062f, -1.1748077f, 0.70762473f, -0.60630405f, 1.3871887f,
            -6.377945f, 0.0f, 1.320963E-7f, 1.2991289E-7f, 0.0f, 3.587594f, 0.0f, 0.0f, 5.5757766E-7f, 0.0f, 1.5110055f, 1.4860302f, -0.80095625f, 0.80449915f, -0.5179287f, 1.4741032f,
            -3.8752222f, 0.0f, 8.026135E-8f, 7.893472E-8f, 0.0f, 2.1798124f, 0.0f, 0.0f, 3.3878268E-7f, 0.0f, 0.9180829f, 0.90290797f, 1.2593482f, 0.41496968f, -0.8658401f, 1.1319425f,
            -3.9728992f, 0.0f, 8.228438E-8f, 8.092431E-8f, 0.0f, 2.2347558f, 0.0f, 0.0f, 3.4732187E-7f, 0.0f, 0.9412236f, 0.9256662f, -0.67931443f, 0.27698618f, -0.81562597f, 1.1813266f,
            -4.6381984f, 0.0f, 9.606368E-8f, 9.447585E-8f, 0.0f, 2.6089866f, 0.0f, 0.0f, 4.054842E-7f, 0.0f, 1.0988404f, 1.0806777f, 1.2810447f, 0.6979763f, -0.55884105f, 1.4338672f,
            -3.2983086f, 0.0f, 6.831266E-8f, 6.718352E-8f, 0.0f, 1.8552986f, 0.0f, 0.0f, 2.883473E-7f, 0.0f, 0.7814057f, 0.7684899f, -0.9204562f, 0.24382757f, -0.93635553f, 1.0625926f,
            -6.340548f, 0.0f, 1.3132176E-7f, 1.2915115E-7f, 0.0f, 3.5665581f, 0.0f, 0.0f, 5.543083E-7f, 0.0f, 1.5021458f, 1.4773169f, 1.4040103f, 0.85206586f, -0.36231804f, 1.627142f,
            -5.865186f, 0.0f, 1.2147633E-7f, 1.1946845E-7f, 0.0f, 3.2991672f, 0.0f, 0.0f, 5.1275083E-7f, 0.0f, 1.3895272f, 1.3665599f, -1.535772f, 0.31127483f, -0.66341114f, 1.3310255f,
            -4.0013504f, 0.0f, 8.2873655E-8f, 8.150384E-8f, 0.0f, 2.2507598f, 0.0f, 0.0f, 3.498092E-7f, 0.0f, 0.9479641f, 0.93229526f, 1.0469139f, 0.13166939f, -0.99014187f, 1.0096953f,
            -5.4745264f, 0.0f, 1.1338521E-7f, 1.1151108E-7f, 0.0f, 3.0794213f, 0.0f, 0.0f, 4.785983E-7f, 0.0f, 1.2969757f, 1.2755381f, 0.29921284f, 0.05024764f, -0.9946684f, 1.0052435f,
            -5.747042f, 0.0f, 1.190294E-7f, 1.1706197E-7f, 0.0f, 3.232711f, 0.0f, 0.0f, 5.024224E-7f, 0.0f, 1.3615377f, 1.3390329f, 1.1001779f, 0.3279176f, -0.6872591f, 1.3075718f,
            -3.9877403f, 0.0f, 8.259176E-8f, 8.122661E-8f, 0.0f, 2.243104f, 0.0f, 0.0f, 3.4861932E-7f, 0.0f, 0.94473964f, 0.9291241f, 0.2928476f, 0.08677202f, -0.6573889f, 1.3369482f,
            -4.289929f, 0.0f, 8.885053E-8f, 8.738192E-8f, 0.0f, 2.4130852f, 0.0f, 0.0f, 3.7503753E-7f, 0.0f, 1.0163316f, 0.99953264f, -2.2630472f, 0.51469547f, -0.49159032f, 1.5000063f,
            -6.2257457f, 0.0f, 1.2894404E-7f, 1.2681274E-7f, 0.0f, 3.501982f, 0.0f, 0.0f, 5.4427204E-7f, 0.0f, 1.4749478f, 1.4505686f, -0.50569254f, 0.11830425f, -0.5848589f, 1.4082794f,
            -2.4586217f, 0.0f, 5.0921546E-8f, 5.007987E-8f, 0.0f, 1.3829747f, 0.0f, 0.0f, 2.1493955E-7f, 0.0f, 0.58247465f, 0.57284695f, 1.4370425f, -0.32666814f, -0.96420014f, 1.0352082f,
            -6.089745f, 0.0f, 1.2612726E-7f, 1.2404251E-7f, 0.0f, 3.4254818f, 0.0f, 0.0f, 5.323824E-7f, 0.0f, 1.4427278f, 1.418881f, 0.33814147f, 0.2600088f, -0.34400254f, 1.6451547f,
            -6.105819f, 0.0f, 1.2646018E-7f, 1.2436993E-7f, 0.0f, 3.4345233f, 0.0f, 0.0f, 5.3378767E-7f, 0.0f, 1.446536f, 1.4226263f, 0.19286832f, 0.25070384f, -0.6816665f, 1.313072f,
            -3.3208535f, 0.0f, 6.877959E-8f, 6.764274E-8f, 0.0f, 1.8679801f, 0.0f, 0.0f, 2.9031824E-7f, 0.0f, 0.7867468f, 0.77374274f, 0.054462824f, -0.39409885f, -0.9726451f, 1.0269029f,
            -3.8304706f, 0.0f, 7.9334484E-8f, 7.802317E-8f, 0.0f, 2.1546397f, 0.0f, 0.0f, 3.3487038E-7f, 0.0f, 0.9074807f, 0.892481f, -0.5420139f, 0.0013477113f, -0.6075902f, 1.3859237f,
            -6.2405934f, 0.0f, 1.2925156E-7f, 1.2711517E-7f, 0.0f, 3.510334f, 0.0f, 0.0f, 5.4557006E-7f, 0.0f, 1.4784654f, 1.454028f, 0.03219004f, -0.43874544f, -0.8519819f, 1.1455716f,
            -2.3794694f, 0.0f, 4.9282185E-8f, 4.8467605E-8f, 0.0f, 1.3384515f, 0.0f, 0.0f, 2.0801981E-7f, 0.0f, 0.56372255f, 0.5544048f, 1.4062479f, -0.5463625f, -0.92878354f, 1.0700395f,
            -4.3292947f, 0.0f, 8.966584E-8f, 8.818376E-8f, 0.0f, 2.4352283f, 0.0f, 0.0f, 3.7847897E-7f, 0.0f, 1.0256577f, 1.0087047f, -0.87205166f, -0.64354575f, -0.98906875f, 1.0107507f,
            -4.345956f, 0.0f, 9.0010914E-8f, 8.852313E-8f, 0.0f, 2.4446f, 0.0f, 0.0f, 3.7993553E-7f, 0.0f, 1.0296049f, 1.0125866f, -1.8783154f, -0.23692346f, -0.8405692f, 1.1567957f,
            -4.82763f, 0.0f, 9.9987076E-8f, 9.83344E-8f, 0.0f, 2.715542f, 0.0f, 0.0f, 4.2204485E-7f, 0.0f, 1.1437188f, 1.1248144f, 0.6175144f, -0.41088927f, -0.6968707f, 1.2981191f,
            -3.844531f, 0.0f, 7.962569E-8f, 7.830957E-8f, 0.0f, 2.1625488f, 0.0f, 0.0f, 3.360996E-7f, 0.0f, 0.91081184f, 0.8957571f, 0.5027408f, 0.062461987f, -0.59946764f, 1.3939121f,
            -2.1673234f, 0.0f, 4.4888342E-8f, 4.4146386E-8f, 0.0f, 1.2191193f, 0.0f, 0.0f, 1.8947341E-7f, 0.0f, 0.5134628f, 0.5049758f, 1.0662752f, -0.42544496f, -0.7224076f, 1.2730042f,
            -5.3415356f, 0.0f, 1.1063079E-7f, 1.0880218E-7f, 0.0f, 3.0046139f, 0.0f, 0.0f, 4.669719E-7f, 0.0f, 1.2654687f, 1.2445519f, 2.4112868f, -0.05685222f, -0.3747844f, 1.6148815f,
            -2.8186655f, 0.0f, 5.8378564E-8f, 5.741363E-8f, 0.0f, 1.5854993f, 0.0f, 0.0f, 2.4641557E-7f, 0.0f, 0.66777295f, 0.65673536f, 0.7816399f, 0.16316691f, -0.4110006f, 1.5792639f,
            -5.8827252f, 0.0f, 1.2183959E-7f, 1.1982571E-7f, 0.0f, 3.309033f, 0.0f, 0.0f, 5.142842E-7f, 0.0f, 1.3936825f, 1.3706464f, -0.61859494f, -0.19603947f, -0.7306398f, 1.2649081f,
            -5.0606527f, 0.0f, 1.0481331E-7f, 1.0308086E-7f, 0.0f, 2.8466172f, 0.0f, 0.0f, 4.4241634E-7f, 0.0f, 1.1989244f, 1.1791075f, -0.209544f, -0.2999919f, -0.38427818f, 1.6055448f,
            -5.243315f, 0.0f, 1.08596495E-7f, 1.06801515E-7f, 0.0f, 2.9493647f, 0.0f, 0.0f, 4.5838516E-7f, 0.0f, 1.2421992f, 1.2216669f, -1.1140529f, -0.6595374f, -0.7284234f, 1.2670878f,
            -4.0317907f, 0.0f, 8.350411E-8f, 8.2123876E-8f, 0.0f, 2.2678823f, 0.0f, 0.0f, 3.5247032E-7f, 0.0f, 0.9551757f, 0.9393877f, 0.44996905f, -0.32271457f, -0.71922755f, 1.2761316f,
            -5.9476414f, 0.0f, 1.231841E-7f, 1.2114799E-7f, 0.0f, 3.3455482f, 0.0f, 0.0f, 5.199593E-7f, 0.0f, 1.4090618f, 1.3857715f, -0.43829054f, -0.83336365f, -0.80248773f, 1.1942477f,
            -3.8971646f, 0.0f, 8.0715814E-8f, 7.938167E-8f, 0.0f, 2.1921551f, 0.0f, 0.0f, 3.4070098E-7f, 0.0f, 0.9232813f, 0.90802044f, -2.1296551f, -0.36747137f, -0.7406287f, 1.2550843f,
            -2.9339974f, 0.0f, 6.076725E-8f, 5.976283E-8f, 0.0f, 1.6503736f, 0.0f, 0.0f, 2.564982E-7f, 0.0f, 0.6950964f, 0.68360716f, -0.17810394f, -0.4910022f, -0.45885748f, 1.5321982f,
            -5.510035f, 0.0f, 1.1412065E-7f, 1.1223436E-7f, 0.0f, 3.0993948f, 0.0f, 0.0f, 4.8170256E-7f, 0.0f, 1.3053881f, 1.2838115f, -2.0283718f, -0.61192185f, -0.92898285f, 1.0698434f,
            -4.978693f, 0.0f, 1.031158E-7f, 1.0141141E-7f, 0.0f, 2.800515f, 0.0f, 0.0f, 4.3525117E-7f, 0.0f, 1.1795073f, 1.1600113f, -0.8212585f, -1.0644013f, -0.9477241f, 1.051412f,
            -5.191355f, 0.0f, 1.07520336E-7f, 1.0574314E-7f, 0.0f, 2.9201372f, 0.0f, 0.0f, 4.538427E-7f, 0.0f, 1.2298893f, 1.2095605f, 1.1151627f, -1.1005746f, -0.9689056f, 1.0305805f,
            -2.3857305f, 0.0f, 4.9411867E-8f, 4.859514E-8f, 0.0f, 1.3419734f, 0.0f, 0.0f, 2.0856719E-7f, 0.0f, 0.5652059f, 0.5558636f, 0.098813176f, -0.91127354f, -0.74540377f, 1.2503881f
    };

    private float mFSParamsValues[] = {
            0.20416708f    ,		            0.0f,
            0.27980208f    ,                    0.0f,
            0.009143527f   ,                    0.0f,
            0.062485732f   ,                    0.0f,
            0.2630213f     ,                    0.0f,
            0.08762257f    ,                    0.0f,
            0.20171137f    ,                    0.0f,
            0.17613323f    ,                    0.0f,
            0.13819848f    ,                    0.0f,
            0.033225294f   ,                    0.0f,
            0.13252299f    ,                    0.0f,
            0.13240615f    ,                    0.0f,
            0.1409756f     ,                    0.0f,
            0.087053806f   ,                    0.0f,
            0.10621458f    ,                    0.0f,
            0.083866104f   ,                    0.0f,
            0.06350184f    ,                    0.0f,
            0.053768713f   ,                    0.0f,
            0.09670098f    ,                    0.0f,
            0.05989486f    ,                    0.0f,
            0.06960367f    ,                    0.0f,
            0.098293126f   ,                    0.0f,
            0.048699226f   ,                    0.0f,
            0.03185607f    ,                    0.0f,
            0.06301964f    ,                    0.0f,
            0.052278396f   ,                    0.0f,
            0.06407753f    ,                    0.0f,
            0.03788286f    ,                    0.0f,
            0.030334948f   ,                    0.0f,
            0.017670825f   ,                    0.0f,
            0.0f           ,                    0.0f,
            0.0f           ,                    0.0f,
            0.0f           ,                    0.0f,
            0.0f           ,                    0.0f,
            0.0f           ,                    0.0f,
            0.0f           ,                    0.0f,
            0.0f           ,                    0.0f,
            0.21397206f    ,                    0.0f,
            1.0907131f     ,                    0.0f,
            0.9030114f     ,                    0.0f,
            0.8272965f     ,                    0.0f,
            0.73757786f    ,                    0.0f,
            0.5776904f     ,                    0.0f,
            0.77220464f    ,                    0.0f,
            0.06254935f    ,                    0.0f,
            0.29369098f    ,                    0.0f,
            0.9282499f     ,                    0.0f,
            0.9031905f     ,                    0.0f,
            0.71087974f    ,                    0.0f,
            0.74244285f    ,                    0.0f,
            0.16224459f    ,                    0.0f,
            0.80932313f    ,                    0.0f,
            0.70788175f    ,                    0.0f,
            0.048699822f   ,                    0.0f,
            0.6641013f     ,                    0.0f,
            0.9321543f     ,                    0.0f,
            0.5769717f     ,                    0.0f,
            0.22144201f    ,                    0.0f,
            0.46618935f    ,                    0.0f,
            0.5933195f     ,                    0.0f,
            0.84696364f    ,                    0.0f,
            0.63227725f    ,                    0.0f,
            0.11252998f    ,                    0.0f,
            0.70666564f    ,                    0.0f,
            0.77711385f    ,                    0.0f,
            0.74849105f    ,                    0.0f,
            0.79333836f    ,                    0.0f,
            0.6295482f     ,                    0.0f,
            0.73013854f    ,                    0.0f,
            0.23823588f    ,                    0.0f,
            0.5902031f     ,                    0.0f,
            0.40691665f    ,                    0.0f,
            0.1870213f     ,                    0.0f,
            0.54043144f    ,                    0.0f,
            0.328143f      ,                    0.0f,
            0.12844934f    ,                    0.0f,
            0.4487042f     ,                    0.0f,
            0.20172663f    ,                    0.0f,
            0.0676617f     ,                    0.0f,
            0.20343925f    ,                    0.0f,
            0.59534013f    ,                    0.0f,
            0.5904934f     ,                    0.0f,
            0.20579901f    ,                    0.0f,
            0.20219225f    ,                    0.0f,
            0.35541344f    ,                    0.0f,
            0.39963108f    ,                    0.0f,
            0.3878631f     ,                    0.0f,
            0.34496546f    ,                    0.0f,
            0.049056087f   ,                    0.0f,
            0.12341197f    ,                    0.0f,
            0.2426543f     ,                    0.0f,
            0.05729884f    ,                    0.0f,
            0.28933862f    ,                    0.0f,
            0.09398336f    ,                    0.0f,
            0.06842917f    ,                    0.0f,
            0.17209603f    ,                    0.0f,
            0.2934833f     ,                    0.0f,
            0.13177204f    ,                    0.0f,
            0.18119915f    ,                    0.0f,
            0.24587427f    ,                    0.0f
    };

    // bg buf 3
    private float bgBuf3[] = {
            0.004f, 	0.0157f, 	0.0275f, 	1.0f,
            0.0471f,    0.157f, 	0.2078f, 	1.0f,
            0.0471f,    0.157f, 	0.2078f, 	1.0f,
            0.004f, 	0.0157f, 	0.0275f, 	1.0f};


    private float bgBuf1[] = {
            -0.5625f, 		1f, 		-116f,
            -0.5625f,       -1f, 		-116f,
            0.5625f, 		-1f,        -116f,
            0.5625f, 		1f, 		-116f };

    private int bgBuf4[] = {
            0,     1,     2,     2,     3,     0
    };

    private int[] mBGaVertexCoordBuf = new int[1];
    private int[] mBGaVertexColorBuf = new int[1];
    private int[] mBGIndexBuf = new int[1];


    private int mBGVertexCoordHandle;
    private int mBGVertexColorHandle;

}
