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
    Serial.print(mpu.getYaw() / 180, 2);
    Serial.print(" ");
    Serial.print(mpu.getPitch() / 180, 2);
    Serial.print(" ");
    Serial.println(mpu.getRoll() / 180, 2);
//    SerialBT.print(mpu.getYaw() / 180, 2);
//    SerialBT.print(" ");
//    SerialBT.print(mpu.getPitch() / 180, 2);
//    SerialBT.print(" ");
//    SerialBT.println(mpu.getRoll() / 180, 2);
}

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
    delay(1000);
    mpu.selectFilter(QuatFilterSel::MAHONY);
    delay(1000);
}

int count = 0;
float avgYaw = 0.0f, avgPitch = 0.0f, avgRoll = 0.0f;
int ansck, actck[3];
float ansAct[3][2] = {{0.15f, 0.00f}, {0.05f, 0.50f}, {0.05f, 1.00f}};
float actPitch, actRoll, actPoint[3];

void loop() {
    if (mpu.update() && !ansck) {
        static uint32_t prev_ms = millis();
        if (millis() > prev_ms + 25) {
            print_roll_pitch_yaw();
            if(!actck[0])
            {
              static uint32_t act0_ms = millis();
              actPitch = 1.00f - abs(ansAct[0][0] - mpu.getPitch() / 180) * 10.00f;
              actRoll = (1.00f - abs(ansAct[0][1] - mpu.getRoll() / 180) * 10.00f);
              actPoint[0] = (actPitch + actRoll) / 2;
              if(millis() > act0_ms + 2000)
              {
                SerialBT.println("wrong action0");
                SerialBT.print("Pitch: ");
                SerialBT.print(actPitch);
                SerialBT.print(", Roll: ");
                SerialBT.println(actRoll);
                act0_ms = millis();
              }
              if(actPoint[0] >= 0.6f)
              {
                SerialBT.println("act0 success");
                actck[0] = 1;
              }
            }
            else
            {
              if(!actck[1])
              {
                static uint32_t act1_ms = millis();
                actPitch = 1.00f - abs(ansAct[1][0] - mpu.getPitch() / 180) * 10.00f;
                actRoll = (1.00f - abs(ansAct[1][1] - mpu.getRoll() / 180) * 10.00f);
                actPoint[1] = (actPitch + actRoll) / 2;
                if(millis() > act1_ms + 2000)
                {
                  SerialBT.println("wrong action1");
                  SerialBT.print("Pitch: ");
                  SerialBT.print(actPitch);
                  SerialBT.print(", Roll: ");
                  SerialBT.println(actRoll);
                  act1_ms = millis();
                }
                if(actPoint[1] >= 0.6f)
                {
                  SerialBT.println("act1 success");
                  actck[1] = 1;
                }
              }
              else
              {
                if(!actck[2])
                {
                  static uint32_t act2_ms = millis();
                  actPitch = 1.00f - abs(ansAct[2][0] - mpu.getPitch() / 180) * 10.00f;
                  actRoll = (1.00f - abs(ansAct[2][1] - mpu.getRoll() / 180) * 10.00f);
                  actPoint[2] = (actPitch + actRoll) / 2;
                  if(millis() > act2_ms + 2000)
                  {
                    SerialBT.println("wrong action1");
                    SerialBT.print("Pitch: ");
                    SerialBT.print(actPitch);
                    SerialBT.print(", Roll: ");
                    SerialBT.println(actRoll);
                    act2_ms = millis();
                  }
                  if(actPoint[2] >= 0.6f)
                  {
                    SerialBT.println("act2 success");
                    actck[2] = 1;
                    ansck = 1;
                  }
                }
              }
            }
            prev_ms = millis();
        }
    }
    else if (ansck == 1) {
      SerialBT.println("!!!!!!!!!!! Your Score !!!!!!!!!!!!");
      SerialBT.print("act0: ");
      SerialBT.print(actPoint[0]);
      SerialBT.print(", act1: ");
      SerialBT.print(actPoint[1]);
      SerialBT.print(", act2: ");
      SerialBT.println(actPoint[2]);
      ansck = 2;
    }
//    if (mpu.update()) {
//        static uint32_t prev_ms = millis();
//        if (millis() > prev_ms + 25) {
//            print_roll_pitch_yaw();
//            prev_ms = millis();
//        }
//    }
//    if (mpu.update() && count < 600) {
//      static uint32_t prev_ms = millis();
//      if (millis() > prev_ms + 25) {
//        count++;
//        print_roll_pitch_yaw();
//        avgYaw += mpu.getYaw();
//        avgPitch += mpu.getPitch();
//        avgRoll += mpu.getRoll();
//        prev_ms = millis();
//      }
//    }
//    if(count == 600)
//    {
//      Serial.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//      Serial.print(avgYaw / 600 / 180, 2);
//      Serial.print(" ");
//      Serial.print(avgPitch / 600 / 180, 2);
//      Serial.print(" ");
//      Serial.println(avgRoll / 600 / 180, 2);
//      Serial.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//      avgYaw = 0, avgPitch = 0, avgRoll = 0, count = 0;
//    }
}
