package com.sdk.qr.ui.scan.screen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.sdk.qr.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    onBarcodeScanned: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var permissionStatus by remember { mutableStateOf(checkPermission(context)) }

    Box(modifier = modifier.fillMaxSize()) {
        when (permissionStatus) {
            PermissionStatus.GRANTED -> {
                CameraPreview(
                    onBarcodeScanned = onBarcodeScanned,
                    modifier = Modifier.fillMaxSize()
                )
            }

            PermissionStatus.DENIED -> {
                PermissionRequestContent(
                    onRequestPermission = {
                        requestPermission(context) { granted ->
                            permissionStatus = if (granted) {
                                PermissionStatus.GRANTED
                            } else {
                                PermissionStatus.DENIED
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private enum class PermissionStatus {
    GRANTED,
    DENIED
}

private fun checkPermission(context: android.content.Context): PermissionStatus {
    return if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        PermissionStatus.GRANTED
    } else {
        PermissionStatus.DENIED
    }
}

private fun requestPermission(
    context: android.content.Context,
    callback: (Boolean) -> Unit
) {
    val activity = context as? Activity
    if (activity != null) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
        callback(false)
    }
}

private const val CAMERA_PERMISSION_REQUEST_CODE = 1001

@Composable
private fun PermissionRequestContent(
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "Camera",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kamera ruxsati kerak",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "QR kod va barkod skanerlash uchun kameraga ruxsat bering.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                try {
                    val intent = android.content.Intent(
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    )
                    intent.data = android.net.Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                } catch (_: Exception) { }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Settings"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Settings ochish")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onRequestPermission
        ) {
            Text("Qayta tekshirish")
        }
    }
}

@Composable
private fun CameraPreview(
    onBarcodeScanned: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var scannedText by remember { mutableStateOf("") }

    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    setupCamera(
                        context = ctx,
                        lifecycleOwner = lifecycleOwner,
                        previewView = this,
                        onBarcodeScanned = { result ->
                            if (scannedText != result) {
                                scannedText = result
                                onBarcodeScanned(result)
                            }
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        ScanOverlay(modifier = Modifier.fillMaxSize())

        if (scannedText.isNotEmpty()) {
            ResultCard(
                text = scannedText,
                onDismiss = { scannedText = "" },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun ScanOverlay(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(250.dp)
                .background(Color.Transparent)
        ) {
            val cornerSize = 24.dp
            val cornerThickness = 3.dp

            // Top-left
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(cornerSize, cornerThickness)
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(cornerThickness, cornerSize)
                    .background(Color.White)
            )
            // Top-right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(cornerSize, cornerThickness)
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(cornerThickness, cornerSize)
                    .background(Color.White)
            )
            // Bottom-left
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(cornerSize, cornerThickness)
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(cornerThickness, cornerSize)
                    .background(Color.White)
            )
            // Bottom-right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(cornerSize, cornerThickness)
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(cornerThickness, cornerSize)
                    .background(Color.White)
            )
        }

        Text(
            text = "QR kodni ramka ichiga joylashtiring",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 150.dp)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ResultCard(
    text: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Skanerlangan natija:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Yopish")
                }
            }
        }
    }
}

private fun setupCamera(
    context: android.content.Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onBarcodeScanned: (String) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val executor: ExecutorService = Executors.newSingleThreadExecutor()

    cameraProviderFuture.addListener({
        try {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(executor, BarcodeAnalyzer(onBarcodeScanned))
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )
        } catch (_: Exception) { }
    }, ContextCompat.getMainExecutor(context))
}

private class BarcodeAnalyzer(
    private val onBarcodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_CODE_128,
            Barcode.FORMAT_CODE_39,
            Barcode.FORMAT_EAN_13,
            Barcode.FORMAT_EAN_8,
            Barcode.FORMAT_DATA_MATRIX
        )
        .build()

    private val scanner: BarcodeScanner = BarcodeScanning.getClient(options)
    private var lastScannedTime = 0L

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastScannedTime > 1000) {
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { value ->
                                if (value.isNotBlank()) {
                                    lastScannedTime = currentTime
                                    onBarcodeScanned(value)
                                    break
                                }
                            }
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
