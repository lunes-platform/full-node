package io.lunes.lang

import io.lunes.lang.v1.BaseGlobal
import io.lunes.utils.{Base58, Base64}
import scorex.crypto.hash.{Blake2b256, Keccak256, Sha256}
import scorex.crypto.signatures.{Curve25519, PublicKey, Signature}

object Global extends BaseGlobal {
  def base58Encode(input: Array[Byte]): Either[String, String] =
    if (input.length > MaxBase58Bytes) Left(s"base58Encode input exceeds $MaxBase58Bytes")
    else Right(Base58.encode(input))

  def base58Decode(input: String, limit: Int): Either[String, Array[Byte]] =
    if (input.length > limit) Left(s"base58Decode input exceeds $limit")
    else Base58.decode(input).toEither.left.map(_ => "can't parse Base58 string")

  def base64Encode(input: Array[Byte]): Either[String, String] = Right(Base64.encode(input))

  def base64Decode(input: String, limit: Int): Either[String, Array[Byte]] =
    if (input.length > limit) Left(s"base58Decode input exceeds $limit")
    else Base64.decode(input).toEither.left.map(_ => "can't parse Base64 string")

  def curve25519verify(message: Array[Byte], sig: Array[Byte], pub: Array[Byte]): Boolean = Curve25519.verify(Signature(sig), message, PublicKey(pub))

  def keccak256(message: Array[Byte]): Array[Byte]  = Keccak256.hash(message)
  def blake2b256(message: Array[Byte]): Array[Byte] = Blake2b256.hash(message)
  def sha256(message: Array[Byte]): Array[Byte]     = Sha256.hash(message)
}
