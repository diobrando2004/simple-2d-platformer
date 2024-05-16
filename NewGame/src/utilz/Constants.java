package utilz;

import main.Game;

public class Constants {
	public static class EnemyConstant {
		public static final int SKELE = 0;
		public static final int IDLE = 3;
		public static final int RUNNING = 2;
		public static final int ATTACK = 0;
		public static final int HIT = 4;
		public static final int DEAD = 1;
		public static final int SKELE_WIDTH_DEFAULT = 64;
		public static final int SKELE_HEIGHT_DEFAULT = 64;
		public static final int SKELE_WIDTH = (int) (SKELE_WIDTH_DEFAULT * Game.scale);
		public static final int SKELE_HEIGHT = (int) (SKELE_HEIGHT_DEFAULT * Game.scale);
		public static final int SKELE_DRAWOFFSET_X = (int) (26 * Game.scale);
		public static final int SKELE_DRAWOFFSET_Y = (int) (8 * Game.scale);

		public static final int CACO = 5;
		public static final int CACO_WIDTH_DEFAULT = 64;
		public static final int CACO_HEIGHT_DEFAULT = 64;
		public static final int CACO_WIDTH = (int) (CACO_WIDTH_DEFAULT * Game.scale);
		public static final int CACO_HEIGHT = (int) (CACO_HEIGHT_DEFAULT * Game.scale);
		public static final int CACO_DRAWOFFSET_X = (int) (26 * Game.scale);
		public static final int CACO_DRAWOFFSET_Y = (int) (8 * Game.scale);

		public static int getSpriteAmount(int enemyType, int enemyState) {
			switch (enemyType) {
			case SKELE:
				switch (enemyState) {
				case IDLE:
					return 4;
				case RUNNING:
					return 12;
				case ATTACK:
					return 13;
				case HIT:
					return 3;
				case DEAD:
					return 13;
				}
			case CACO:
				switch (enemyState) {
				case RUNNING:
				case IDLE:
					return 6;
				case ATTACK:
					return 6;
				case HIT:
					return 4;
				case DEAD:
					return 8;
				}
			}
			return 0;
		}

		public static int getMaxHealth(int enemy_type) {
			switch (enemy_type) {
			case SKELE:
				return 10;
			default:
				return 1;
			}
		}

		public static int getEnemyDmg(int enemy_type) {
			switch (enemy_type) {
			case SKELE:
				return 15;
			default:
				return 0;
			}
		}
	}

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.scale);
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.scale);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.scale);
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.scale);
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 61;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.scale);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.scale);
		}
	}

	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int DOWN = 3;
		public static final int RIGHT = 2;
	}

	public static class PlayerConstants {
		public static final int running = 1;
		public static final int Idle = 0;
		public static final int Jumping = 3;
		public static final int Falling = 5;
		public static final int attack = 2;
		public static final int walkattack = 4;
		public static final int DEAD = 6;
		public static final int HIT = 7;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case running:
				return 6;
			case Idle:
				return 4;
			case attack:
				return 4;
			case walkattack:
				return 3;
			case Jumping:
				return 4;
			case Falling:
				return 4;
			case DEAD:
				return 8;
			case HIT:
				return 4;
			default:
				return -1;
			}
		}
	}
}
