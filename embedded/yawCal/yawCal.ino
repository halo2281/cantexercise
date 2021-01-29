#include "MPU9250.h"
#include "BluetoothSerial.h"
#include "eeprom_utils.h"

MPU9250 mpu;
BluetoothSerial SerialBT;

void calib() {
    Serial.println("Accel Gyro calibration will start in 3sec.");
    Serial.println("Please leave the device still on the flat plane.");
    mpu.verbose(true);
    delay(3000);
    Serial.println("Start");
    mpu.calibrateAccelGyro();
  
    Serial.println("Mag calibration will start in 3sec.");
    Serial.println("Please Wave device in a figure eight until done.");
    delay(3000);
    Serial.println("Start");
    mpu.calibrateMag();

    printCalibration();
    mpu.verbose(false);

    // save to eeprom
    saveCalibration();

    // load from eeprom
    loadCalibration();
}

void print_roll_pitch_yaw() {
    Serial.print(mpu.getYaw(), 6);
    Serial.print(" ");
    Serial.print(mpu.getPitch(), 6);
    Serial.print(" ");
    Serial.println(mpu.getRoll(), 6);
    SerialBT.print(mpu.getYaw() / 180, 2);
    SerialBT.print(" ");
    SerialBT.print(mpu.getPitch() / 180, 2);
    SerialBT.print(" ");
    SerialBT.println(mpu.getRoll() / 180, 2);
}
float prevYaw;
void setup() {
    Serial.begin(115200);
    SerialBT.begin("right-wrist");
    Wire.begin();
    delay(2000);
    
    if (!EEPROM.begin(EEPROM_SIZE))
    {
      Serial.println("failed to initialise EEPROM"); delay(1000000);
    }
    
    if (!mpu.setup(0x68)) {  // change to your own address
        while (1) {
            Serial.println("MPU connection failed. Please check your connection with `connection_check` example.");
            delay(5000);
        }
    }

    if(!isCalibrated()) {
      calib();
    }
    setupEEPROM();
    mpu.selectFilter(QuatFilterSel::MAHONY);
}

float sumYaw = 0.0f;
int cnt = 0;
void loop() {
    if (mpu.update()) {
        static uint32_t prev_ms = millis();
        static float prevYaw = 888;
        if (millis() >= prev_ms + 30000) {
            float curYaw = mpu.getYaw();
            float dYaw;
            if(prevYaw >= 888) prevYaw = curYaw;
            else
            {
              Serial.print("prevYaw: ");
              Serial.print(prevYaw);
              Serial.print(", curYaw: ");
              Serial.println(curYaw);
              Serial.print("dYaw: ");
              if(prevYaw < 0 && curYaw < 0)
              {
                dYaw = prevYaw - curYaw;
                Serial.println(dYaw);
              }
              else if(prevYaw < 0 && curYaw >= 0)
              {
                dYaw = prevYaw - curYaw + 360;
                Serial.println(dYaw);
              }
              else if(prevYaw >= 0 && curYaw >= 0)
              {
                dYaw = curYaw - prevYaw;
                Serial.println(dYaw);
              }
              else
              {
                dYaw = 360 - (prevYaw - curYaw);
                Serial.println(dYaw);
              }
              sumYaw += dYaw;
              cnt += 1;
              prevYaw = curYaw;
              if(cnt > 20)
              {
                Serial.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Serial.print("sumYaw(10min): ");
                Serial.println(sumYaw);
                Serial.print("avgYaw(/s): ");
                Serial.println(sumYaw / 600);
                Serial.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                sumYaw = 0;
                cnt = 0;
              }
            }
            prev_ms = millis();
        }
    }
}
